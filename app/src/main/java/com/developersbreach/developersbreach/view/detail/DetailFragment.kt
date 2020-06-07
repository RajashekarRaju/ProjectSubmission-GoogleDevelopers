package com.developersbreach.developersbreach.view.detail

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2

import com.developersbreach.developersbreach.databinding.FragmentDetailBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.ZoomOutPageTransformer
import com.developersbreach.developersbreach.viewModel.DetailViewModel
import com.developersbreach.developersbreach.viewModel.factory.DetailViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: Bundle = requireArguments()
        val application: Application = requireActivity().application
        val article: Articles = DetailFragmentArgs.fromBundle(args).detailFragmentArgs
        val isUserInputEnabled: Boolean = DetailFragmentArgs.fromBundle(args).fragmentClassArgs
        val factory = DetailViewModelFactory(application, article, isUserInputEnabled)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        viewPager = binding.detailViewPager
        binding.detailViewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.viewPagerList.observe(viewLifecycleOwner, Observer { sportsList ->
            val detailViewPagerAdapter = DetailViewPagerAdapter(viewModel, viewPager)
            detailViewPagerAdapter.submitList(sportsList)
            viewPager.adapter = detailViewPagerAdapter

            viewModel.selectedArticle.observe(viewLifecycleOwner, Observer { selectedArticle ->
                val correctPosition = selectedArticle.id - 1
                viewPager.setCurrentItem(correctPosition, false)
            })

            viewModel.userInputEnabled.observe(viewLifecycleOwner, Observer { isUserInputEnabled ->
                viewPager.isUserInputEnabled = isUserInputEnabled
            })
        })
    }
}
