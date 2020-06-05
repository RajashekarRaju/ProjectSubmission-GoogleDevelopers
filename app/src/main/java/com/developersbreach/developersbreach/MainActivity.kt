package com.developersbreach.developersbreach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.developersbreach.developersbreach.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = this.findNavController(R.id.nav_host_fragment)
        setBottomNavigation(binding.bottomNavigation)
        setDestinationListener(navController)
    }

    private fun setDestinationListener(controller: NavController) {
        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailFragment || destination.id == R.id.webViewFragment ||
                destination.id == R.id.commonWebViewFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun setBottomNavigation(bottomNavigation: BottomNavigationView) {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.articlesFragment -> navigateToDestination(item)
                R.id.searchFragment -> navigateToDestination(item)
                R.id.favoritesFragment -> navigateToDestination(item)
                R.id.settingsFragment -> navigateToDestination(item)
                else -> false
            }
        }
    }

    private fun navigateToDestination(
        item: MenuItem
    ) = (NavigationUI.onNavDestinationSelected(item, navController)
            || super.onOptionsItemSelected(item))
}
