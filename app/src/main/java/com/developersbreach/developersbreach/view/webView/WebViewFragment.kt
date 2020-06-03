package com.developersbreach.developersbreach.view.webView

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentWebViewBinding
import com.developersbreach.developersbreach.viewModel.WebViewModel
import com.developersbreach.developersbreach.viewModel.factory.WebViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class WebViewFragment : Fragment() {

    private lateinit var viewModel: WebViewModel
    private lateinit var binding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: Bundle = requireArguments()
        val application: Application = requireActivity().application
        val articleUrl: String = WebViewFragmentArgs.fromBundle(args).webViewFragmentArgs
        val factory = WebViewModelFactory(application, articleUrl)
        viewModel = ViewModelProvider(this, factory).get(WebViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.articleLink.observe(viewLifecycleOwner, Observer { link ->
            binding.articleUrlLink.text = link.toString()
        })
    }
}
