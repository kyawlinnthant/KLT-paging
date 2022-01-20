package com.example.paging3sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.paging3sample.R
import com.google.android.material.appbar.AppBarLayout
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
                R.id.dest_popular,
                R.id.dest_upcoming
            )
        )
    }
    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }
    private val navView by lazy {
        findViewById<BottomNavigationView>(R.id.btn_nav_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        setSupportActionBar(toolbar)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setExpendToolbar(expended: Boolean, animated: Boolean = true) {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(expended, animated)
    }
}