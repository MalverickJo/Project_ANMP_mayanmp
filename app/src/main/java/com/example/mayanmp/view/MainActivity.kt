package com.example.mayanmp.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mayanmp.R
import com.example.mayanmp.databinding.ActivityMainBinding
import com.example.mayanmp.model.SharePreferences

class MainActivity : AppCompatActivity() {
    private lateinit var sharePreferences: SharePreferences
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

        val navController = navHostFragment?.navController

        if (navController != null) {
            binding.bottomNavBar.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.addBudgetFragment, R.id.editBudgetFragment -> {
                        binding.bottomNavBar.visibility = View.GONE
                    }

                    else -> {
                        binding.bottomNavBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }
}