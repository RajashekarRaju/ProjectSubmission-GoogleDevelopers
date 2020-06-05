package com.developersbreach.developersbreach.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentFavoritesBinding
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
        viewModel.favorites.observe(viewLifecycleOwner, Observer { list ->
            val adapter = FavoritesAdapter(viewModel, this)
            adapter.submitList(list)
            binding.favoritesRecyclerView.adapter = adapter
        })
    }
}
