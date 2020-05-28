package com.developersbreach.developersbreach.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.developersbreach.developersbreach.databinding.FragmentArticlesBinding
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel
import com.developersbreach.developersbreach.viewModel.factory.ArticleViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ArticlesFragment : Fragment() {

    private lateinit var binding: FragmentArticlesBinding
    private lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ArticleViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.articles.observe(viewLifecycleOwner, Observer { list ->
            val adapter = ArticlesAdapter(onArticleClickListener(), viewModel, this)
            adapter.submitList(list)
            binding.articlesRecyclerView.adapter = adapter
        })
    }

    private fun onArticleClickListener(): ArticlesAdapter.OnClickListener {
        return ArticlesAdapter.OnClickListener { article ->
            this.findNavController()
                .navigate(ArticlesFragmentDirections.ArticlesToDetailFragment(article))
        }
    }
}
