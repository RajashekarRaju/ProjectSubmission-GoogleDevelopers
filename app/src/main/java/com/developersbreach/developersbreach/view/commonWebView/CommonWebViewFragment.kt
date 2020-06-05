package com.developersbreach.developersbreach.view.commonWebView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.viewModel.CommonWebViewViewModel

class CommonWebViewFragment : Fragment() {

    private lateinit var viewModel: CommonWebViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_common_web_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommonWebViewViewModel::class.java)

    }

}