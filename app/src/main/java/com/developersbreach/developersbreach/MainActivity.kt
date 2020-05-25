package com.developersbreach.developersbreach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.developersbreach.developersbreach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = this.findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
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
