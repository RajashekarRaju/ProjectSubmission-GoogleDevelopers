package com.developersbreach.developersbreach.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentFavoritesBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.RecyclerViewItemDecoration
import com.developersbreach.developersbreach.utils.startLinearAnimation
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel
import com.developersbreach.developersbreach.viewModel.factory.FavoritesViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoritesViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, FavoritesViewModelFactory(activity.application))
            .get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RecyclerViewItemDecoration.setItemSpacing(resources, binding.favoritesRecyclerView)

        viewModel.favorites.observe(viewLifecycleOwner, Observer { favoriteList ->
            val adapter = FavoritesAdapter(viewModel, this)
            adapter.submitList(favoriteList)
            binding.favoritesRecyclerView.adapter = adapter
            toggleRecyclerView(favoriteList)
        })
    }

    private fun toggleRecyclerView(articleList: List<Articles>) {
        if (articleList.isEmpty()) {
            binding.favoritesRecyclerView.visibility = View.INVISIBLE
            binding.noFavoritesFound.visibility = View.VISIBLE
        } else {
            binding.favoritesRecyclerView.visibility = View.VISIBLE
            binding.noFavoritesFound.visibility = View.INVISIBLE
        }
    }

    /*
     * Call animation from class [ArticleAnimations] and pass view which needs to animate.
     * In our case we animate items of cardView inside RecyclerView.
     */
    override fun onResume() {
        super.onResume()
        startLinearAnimation(binding.favoritesRecyclerView)
    }
}
