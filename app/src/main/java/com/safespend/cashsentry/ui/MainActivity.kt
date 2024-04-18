package com.safespend.cashsentry.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.safespend.cashsentry.R
import com.safespend.cashsentry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CashSentry)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // --------------------> Botton Navigation <------------------------
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_wallet))
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_history))
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_profile))

        binding.meowBottonNavigation.setCount(2, "#")
        binding.meowBottonNavigation.show(1)

        binding.meowBottonNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> navigateToHomeFragment()
                2 -> navigateToNotificationFragment()
                3 -> navigateToProfileFragment()
            }
        }

    }

    private fun navigateToProfileFragment() {
        navController.navigate(R.id.profileFragment)
    }

    private fun navigateToHomeFragment() {
        navController.navigate(R.id.homeFragment)
    }

    private fun navigateToNotificationFragment() {
        navController.navigate(R.id.notificationFragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.meowBottonNavigation.show(1)
    }

}