package com.developersbreach.developersbreach.view.webView

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.databinding.FragmentWebViewBinding
import com.developersbreach.developersbreach.viewModel.WebViewModel
import com.developersbreach.developersbreach.viewModel.factory.WebViewModelFactory
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

/**
 * A simple [Fragment] subclass.
 */
class WebViewFragment : Fragment() {

    private lateinit var viewModel: WebViewModel
    private lateinit var binding: FragmentWebViewBinding

    private lateinit var webView: WebView
    private lateinit var webSettings: WebSettings
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: Bundle = requireArguments()
        val application: Application = requireActivity().application
        val articleUrl: String = WebViewFragmentArgs.fromBundle(args).webViewFragmentArgs
        val factory = WebViewModelFactory(application, articleUrl)
        viewModel = ViewModelProvider(this, factory).get(WebViewModel::class.java)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater)

        webView = binding.includeWebViewContent.webView
        bottomAppBar = binding.webViewBottomAppBar

        webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        setWebViewMenu(bottomAppBar)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeWebView()
    }

    private fun observeWebView() {
        viewModel.articleLink.observe(viewLifecycleOwner, Observer { link ->
            // "https://github.com/"
            webView.loadUrl(link)
        })

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webViewProgressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webViewProgressBar.visibility = View.GONE
            }
        }
    }

    private fun onWebMenuClickListener(): Toolbar.OnMenuItemClickListener {
        return object : MenuItem.OnMenuItemClickListener,
            Toolbar.OnMenuItemClickListener {
            @SuppressLint("NewApi")
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.close_detail_fragment_menu_item -> closeWebView()
                    R.id.refresh_page_detail_fragment_menu_item -> observeWebView()
                    R.id.search_detail_content_menu_item -> searchWebView()
                    R.id.change_theme_detail_fragment_menu_item -> changeWebViewTheme()
                    R.id.find_top_web_view_menu_item -> findPreviousQuery()
                    R.id.find_bottom_web_view_menu_item -> findNextQuery()
                    R.id.clear_matches_web_view_menu_item -> clearMatchesAndSwitchMenu()
                }
                return true
            }
        }
    }

    private fun setWebViewMenu(bottomAppBar: BottomAppBar) {
        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu)
        bottomAppBar.setOnMenuItemClickListener(onWebMenuClickListener())
        bottomAppBar.hideOnScroll = true
        invalidateMenuOptions()
    }

    private fun closeWebView() = Navigation.findNavController(binding.root).navigateUp()

    private fun findNextQuery() = webView.findNext(true)

    private fun findPreviousQuery() = webView.findNext(false)

    @RequiresApi(Build.VERSION_CODES.Q)
    var themeMode = WebSettings.FORCE_DARK_OFF

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun changeWebViewTheme() {
        if (themeMode == WebSettings.FORCE_DARK_ON) {
            webSettings.forceDark = WebSettings.FORCE_DARK_OFF
            themeMode = WebSettings.FORCE_DARK_OFF
        } else if (themeMode == WebSettings.FORCE_DARK_OFF) {
            webSettings.forceDark = WebSettings.FORCE_DARK_ON
            themeMode = WebSettings.FORCE_DARK_ON
        }
    }

    private fun clearMatchesAndSwitchMenu() {
        webView.clearMatches()
        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu)
        invalidateMenuOptions()
    }

    private fun searchWebView() {
        val dialog: AlertDialog =
            MaterialAlertDialogBuilder(context, R.style.MaterialDialog)
                .setTitle(R.string.search_query_dialog_title)
                .setView(R.layout.search_edit_text)
                .setPositiveButton(R.string.search_query_dialog_positive_button, null)
                .setNegativeButton(R.string.search_query_dialog_negative_button, null)
                .create()

        dialog.setOnShowListener { alertDialog ->
            alertDialog as AlertDialog
            val positiveButton: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton: Button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val editText: TextInputEditText = alertDialog.findViewById(R.id.text_input_layout)!!

            positiveButton.setOnClickListener {
                val query: String = editText.text.toString()
                if (query.isEmpty()) {
                    editText.error = getString(R.string.search_query_error_message)
                } else {
                    webView.findAllAsync(query)
                    dialog.dismiss()
                    bottomAppBar.replaceMenu(R.menu.web_view_menu)
                }
            }

            negativeButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        dialog.show()

        val params = dialog.window?.attributes
        params!!.gravity = Gravity.TOP
        dialog.window!!.attributes = params
    }

    private fun invalidateMenuOptions() {
        val item: MenuItem = bottomAppBar.menu.getItem(3)
        item.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }
}
