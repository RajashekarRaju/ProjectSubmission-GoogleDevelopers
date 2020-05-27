package com.developersbreach.developersbreach.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.developersbreach.developersbreach.databinding.FragmentDetailBinding
import com.developersbreach.developersbreach.viewModel.DetailViewModel
import com.developersbreach.developersbreach.viewModel.factory.DetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)

        val article = DetailFragmentArgs.fromBundle(requireArguments()).detailFragmentArgs
        val viewModelFactory = DetailViewModelFactory(application, article)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.lifecycleOwner = this
        return binding.root
    }

}
