package com.example.paging3sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.paging3sample.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment).navController
    }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.dest_remote,
                R.id.dest_local
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {

        findViewById<BottomNavigationView>(R.id.btn_nav_view).setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}