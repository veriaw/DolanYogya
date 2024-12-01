package com.veriaw.dolanyogya.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veriaw.dolanyogya.R
import com.veriaw.dolanyogya.databinding.ActivityMainBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.dolanyogya.ui.auth.LoginActivity
import com.veriaw.kriptografiapp.model.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController   (R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.cityMapsFragment ,R.id.bookmarkFragment,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username",null)
        val userId = sharedPreferences.getInt("userId",0)
        binding.tvGreet.text="Selamat Datang,\n"+"$username"
        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.putBoolean("isLoggedIn",false)
            editor.apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}