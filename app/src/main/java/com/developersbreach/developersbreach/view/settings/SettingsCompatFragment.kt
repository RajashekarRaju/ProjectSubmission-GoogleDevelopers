package com.developersbreach.developersbreach.view.settings

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.databinding.FragmentSettingsBinding
import com.developersbreach.developersbreach.utils.PASS_PREFERENCE_CONTACT_KEY
import com.developersbreach.developersbreach.utils.PASS_PREFERENCE_DEVELOPER_KEY
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.SettingsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsCompatFragment : PreferenceFragmentCompat() {

    companion object {

        private lateinit var viewModel: SettingsViewModel
        private lateinit var fragment: SettingsFragment
        private lateinit var binding: FragmentSettingsBinding
        private lateinit var deletePreference: Preference
        private lateinit var requireContext: Context

        fun newInstance(
            viewModel: SettingsViewModel,
            fragment: SettingsFragment,
            binding: FragmentSettingsBinding
        ): SettingsCompatFragment? {
            this.viewModel = viewModel
            this.fragment = fragment
            this.binding = binding
            return SettingsCompatFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.findFavorites.observe(viewLifecycleOwner, Observer { articles ->
            if (articles.isNotEmpty()) {
                deletePreference.isEnabled = true
            } else {
                deletePreference.isEnabled = false
                deletePreference.icon.alpha = 50
            }
        })
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        requireContext = requireContext()

        val contactPreference: Preference = findPreference("ContactFormKey")!!
        contactPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                navigateToDestination(PASS_PREFERENCE_CONTACT_KEY)
                true
            }

        val refreshPreference: Preference = findPreference("RefreshArticlesKey")!!
        refreshPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                showArticleRefreshDialog()
                true
            }

        deletePreference = findPreference("DeleteAllFavoritesKey")!!
        deletePreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                showArticleDeleteDialog()
                true
            }

        val githubPreference: Preference = findPreference("DeveloperProjectKey")!!
        githubPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                navigateToDestination(PASS_PREFERENCE_DEVELOPER_KEY)
                true
            }

        val aboutPreference: Preference = findPreference("AboutProjectKey")!!
        aboutPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                showAboutAppDialog()
                true
            }
    }

    private fun navigateToDestination(preferenceType: String) {
        val action: NavDirections =
            SettingsFragmentDirections.SettingsToCommonWebViewFragment(
                preferenceType
            )
        Navigation.findNavController(binding.root).navigate(action)
    }


    private fun showArticleRefreshDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.refresh_article_dialog_title))
        dialog.setIcon(R.drawable.ic_refresh)
        dialog.setMessage(getString(R.string.refresh_article_dialog_message))
        dialog.setPositiveButton(
            R.string.refresh_article_dialog_positive_button,
            RefreshArticlesButton()
        )
        dialog.setNegativeButton(R.string.refresh_article_dialog_negative_button, DismissDialog())
        dialog.show()
    }

    private fun showArticleDeleteDialog() {
        val dialog =
            MaterialAlertDialogBuilder(requireContext, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.delete_article_dialog_title))
        dialog.setIcon(R.drawable.ic_delete_all)
        dialog.setMessage(getString(R.string.delete_article_dialog_message))
        dialog.setPositiveButton(
            getString(R.string.delete_article_dialog_positive_button),
            DeleteArticlesButton()
        )
        dialog.setNegativeButton(
            getString(R.string.delete_article_dialog_negative_button),
            DismissDialog()
        )
        dialog.show()
    }

    private fun showAboutAppDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext, R.style.MaterialDialog)
        dialog.setTitle(getString(R.string.about_app_dialog_title))
        dialog.setMessage(getString(R.string.about_app_dialog_message))
        dialog.show()
    }

    class RefreshArticlesButton : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            if (isNetworkConnected(requireContext)) {
                viewModel.refreshData()
                showSnackBar(
                    requireContext.getString(R.string.snackbar_refresh_articles_message),
                    fragment.requireActivity()
                )
            } else {
                showSnackBar(
                    requireContext.getString(R.string.no_internet_connection),
                    fragment.requireActivity()
                )
            }
        }
    }

    private class DeleteArticlesButton : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            viewModel.deleteAllArticles()
            showSnackBar(
                requireContext.getString(R.string.snackbar_delete_articles_message),
                fragment.requireActivity()
            )
            deletePreference.isEnabled = false
            deletePreference.icon.alpha = 50
        }
    }

    private class DismissDialog : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
        }
    }
}
