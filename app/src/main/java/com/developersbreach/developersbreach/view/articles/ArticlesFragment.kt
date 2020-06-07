package com.developersbreach.developersbreach.view.articles

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.databinding.FragmentArticlesBinding
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel
import com.developersbreach.developersbreach.viewModel.factory.ArticleViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
        ((activity) as AppCompatActivity).setSupportActionBar(binding.articlesToolbar)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.isInternetAvailable.observe(viewLifecycleOwner, Observer { isInternetAvailable ->
            if (!isInternetAvailable) {
                showSnackBar(getString(R.string.no_internet_connection), requireActivity())
            }
        })

        viewModel.articles.observe(viewLifecycleOwner, Observer { list ->
            val adapter = ArticlesAdapter(viewModel, this)
            adapter.submitList(list)
            binding.articlesRecyclerView.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile_menu_item -> {
                if (isNetworkConnected(requireContext())) {
                    showProfileDialog()
                } else {
                    showSnackBar(getString(R.string.no_internet_connection), requireActivity())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showProfileDialog() {
        val dialog: AlertDialog = createMaterialDialog()
        setDialogViews(dialog)
        dialog.show()
    }

    private fun createMaterialDialog(): AlertDialog {
        return MaterialAlertDialogBuilder(context, R.style.MaterialDialog)
            .setView(R.layout.profile_layout)
            .create()
    }

    private fun setDialogViews(dialog: AlertDialog) {
        dialog.setOnShowListener { alertDialog ->
            alertDialog as AlertDialog
            val imageView: ImageView = alertDialog.findViewById(R.id.profile_image_view)!!
            val name: TextView = alertDialog.findViewById(R.id.profile_title)!!
            val description: TextView = alertDialog.findViewById(R.id.profile_description)!!

            with(viewModel) {
                getAuthorDetails()
                author.observe(viewLifecycleOwner, Observer { author ->
                    name.text = author.name
                    description.text = author.description
                    Glide.with(requireContext())
                        .load(author.avatar)
                        .placeholder(R.drawable.ic_profile)
                        .circleCrop()
                        .into(imageView)
                })
            }
        }
    }
}
