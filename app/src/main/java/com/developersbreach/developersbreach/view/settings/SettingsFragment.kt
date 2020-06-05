package com.developersbreach.developersbreach.view.settings

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.databinding.FragmentSettingsBinding
import com.developersbreach.developersbreach.viewModel.SettingsViewModel
import com.developersbreach.developersbreach.viewModel.factory.SettingsViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application: Application = requireActivity().application
        val factory = SettingsViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragment: SettingsCompatFragment? = SettingsCompatFragment.newInstance(viewModel, this, binding)
        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.settingsFragmentCompactContainer.id, fragment!!)
        fragmentTransaction.commit()
    }
}
