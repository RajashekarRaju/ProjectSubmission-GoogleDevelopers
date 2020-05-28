package com.developersbreach.developersbreach.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.favorites.observe(viewLifecycleOwner, Observer { list ->
            val adapter = FavoritesAdapter(onFavoriteClickListener(), viewModel)
            adapter.submitList(list)
            binding.favoritesRecyclerView.adapter = adapter
        })
    }

    private fun onFavoriteClickListener(): FavoritesAdapter.OnClickListener {
        return FavoritesAdapter.OnClickListener { article ->
            this.findNavController()
                .navigate(FavoritesFragmentDirections.FavoritesToDetailFragment(article))
        }
    }

}
