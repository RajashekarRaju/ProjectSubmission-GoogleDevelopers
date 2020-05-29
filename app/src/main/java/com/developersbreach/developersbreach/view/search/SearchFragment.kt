package com.developersbreach.developersbreach.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentSearchBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.SearchViewModel
import com.developersbreach.developersbreach.viewModel.factory.SearchViewModelFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, SearchViewModelFactory(activity.application))
            .get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchableArticles()

        binding.headerSearchBar.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(query: CharSequence, start: Int, before: Int, count: Int) {
                queriedArticles(query)
            }

            override fun afterTextChanged(s: Editable?) {
                // Do not perform operation
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do not perform operation
            }
        })
    }

    private fun queriedArticles(query: CharSequence) {
        if (query.isNotEmpty()) {
            val formatQuery: String = query.toString().toLowerCase(Locale.getDefault())
            viewModel.filter(formatQuery).observe(viewLifecycleOwner, Observer { articles ->
                val adapter = SearchAdapter()
                adapter.submitList(articles)
                binding.searchRecyclerView.adapter = adapter
                toggleRecyclerView(articles)
            })
        } else {
            searchableArticles()
        }
    }

    private fun searchableArticles() {
        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            val adapter = SearchAdapter()
            adapter.submitList(articles)
            binding.searchRecyclerView.adapter = adapter
        })
    }

    private fun toggleRecyclerView(articleList: List<Articles>) {
        if (articleList.isEmpty()) {
            binding.searchRecyclerView.visibility = View.INVISIBLE
            binding.noSearchResultsFound.visibility = View.VISIBLE
        } else {
            binding.searchRecyclerView.visibility = View.VISIBLE
            binding.noSearchResultsFound.visibility = View.INVISIBLE
        }
    }
}

