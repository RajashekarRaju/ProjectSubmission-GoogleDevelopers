package com.developersbreach.developersbreach.view.commonWebView

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentCommonWebViewBinding
import com.developersbreach.developersbreach.viewModel.CommonWebViewViewModel
import com.developersbreach.developersbreach.viewModel.factory.CommonWebViewViewModelFactory


class CommonWebViewFragment : Fragment() {

    private lateinit var viewModel: CommonWebViewViewModel
    private lateinit var binding: FragmentCommonWebViewBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: Bundle = requireArguments()
        val application: Application = requireActivity().application
        val webUrl: String = CommonWebViewFragmentArgs.fromBundle(args).webUrlArgs
        val factory = CommonWebViewViewModelFactory(application, webUrl)
        viewModel = ViewModelProvider(this, factory).get(CommonWebViewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommonWebViewBinding.inflate(inflater)
        webView = binding.commonWebView
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}