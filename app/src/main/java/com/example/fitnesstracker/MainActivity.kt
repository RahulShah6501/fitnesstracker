package com.example.fitnesstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_workout_log -> loadFragment(WorkoutLogFragment())
                R.id.nav_progress_tracker -> loadFragment(ProgressTrackerFragment())
            }
            drawerLayout.closeDrawers()
            true
        }

        if (savedInstanceState == null) {
            loadFragment(WorkoutLogFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
