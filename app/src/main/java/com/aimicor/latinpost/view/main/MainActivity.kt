package com.aimicor.latinpost.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aimicor.latinpost.view.R
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }
}