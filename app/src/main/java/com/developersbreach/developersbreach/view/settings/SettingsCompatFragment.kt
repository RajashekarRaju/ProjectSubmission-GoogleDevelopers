package com.developersbreach.developersbreach.view.settings

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.databinding.FragmentSettingsBinding
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.SettingsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingsCompatFragment(
    private val viewModel: SettingsViewModel,
    private val settingsFragment: SettingsFragment,
    private val binding: FragmentSettingsBinding
) : PreferenceFragmentCompat() {

    lateinit var deletePreference: Preference

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.findFavorites.observe(viewLifecycleOwner, Observer {
            val beforeDelete = it.size
            if (beforeDelete >= 1) {
                deletePreference.isEnabled = true
            } else {
                deletePreference.isEnabled = false
                deletePreference.icon.alpha = 50
            }
        })
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val contactPreference: Preference? = findPreference("ContactFormKey")
        if (contactPreference != null) {
            contactPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    Toast.makeText(context, "Contact", Toast.LENGTH_SHORT).show()
                    true
                }
        }

        val refreshPreference: Preference? = findPreference("RefreshArticlesKey")
        if (refreshPreference != null) {
            refreshPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showArticleRefreshDialog(requireContext())
                    true
                }
        }

        deletePreference = findPreference("DeleteAllFavoritesKey")!!
        deletePreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                showArticleDeleteDialog(requireContext(), requireView())
                true
            }

        val githubPreference: Preference? = findPreference("DeveloperProjectKey")
        if (githubPreference != null) {
            githubPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    Toast.makeText(context, "Developer", Toast.LENGTH_SHORT).show()
                    true
                }
        }

        val aboutPreference: Preference? = findPreference("AboutProjectKey")
        if (aboutPreference != null) {
            aboutPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showAboutAppDialog(requireContext())
                    true
                }
        }
    }


    private fun showArticleRefreshDialog(context: Context) {
        val dialog = MaterialAlertDialogBuilder(context, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.refresh_article_dialog_title))
        dialog.setIcon(R.drawable.ic_refresh)
        dialog.setMessage(getString(R.string.refresh_article_dialog_message))
        dialog.setPositiveButton(
            R.string.refresh_article_dialog_positive_button,
            RefreshArticlesButton(viewModel, context, settingsFragment)
        )
        dialog.setNegativeButton(R.string.refresh_article_dialog_negative_button, DismissDialog())
        dialog.show()
    }

    private fun showArticleDeleteDialog(
        context: Context,
        view: View
    ) {
        val dialog =
            MaterialAlertDialogBuilder(context, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.delete_article_dialog_title))
        dialog.setIcon(R.drawable.ic_delete_all)
        dialog.setMessage(getString(R.string.delete_article_dialog_message))
        dialog.setPositiveButton(
            getString(R.string.delete_article_dialog_positive_button),
            DeleteArticlesButton(viewModel, deletePreference, view)
        )
        dialog.setNegativeButton(
            getString(R.string.delete_article_dialog_negative_button),
            DismissDialog()
        )
        dialog.show()
    }

    private fun showAboutAppDialog(context: Context) {
        val dialog = MaterialAlertDialogBuilder(context, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.about_app_dialog_title))
        dialog.setMessage(getString(R.string.about_app_dialog_message))
        dialog.show()
    }

    class RefreshArticlesButton(
        private val viewModel: SettingsViewModel,
        private val context: Context,
        private val fragment: SettingsFragment

    ) : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface, which: Int) {
            if (isNetworkConnected(context)) {
                viewModel.refreshData()
                showSnackBar(
                    context.getString(R.string.snackbar_refresh_articles_message),
                    fragment.requireActivity()
                )
            } else {
                showSnackBar(
                    context.getString(R.string.no_internet_connection),
                    fragment.requireActivity()
                )
            }
        }
    }

    private class DeleteArticlesButton(
        private val viewModel: SettingsViewModel,
        private val deletePreference: Preference,
        private val view: View
    ) : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface, which: Int) {
            viewModel.deleteAllArticles()
            deletePreference.isEnabled = false
            deletePreference.icon.alpha = 50
            Snackbar.make(
                view,
                R.string.snackbar_delete_articles_message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private class DismissDialog : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
        }
    }
}
