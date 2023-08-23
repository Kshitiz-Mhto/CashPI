package com.safespend.cashsentry.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.safespend.cashsentry.R
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import com.safespend.cashsentry.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var database: CashSentryDB

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CashSentry)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --------------------> Botton Navigation <------------------------
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_wallet))
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_notification))
        binding.meowBottonNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_profile))

        binding.meowBottonNavigation.setCount(2, "22")
        binding.meowBottonNavigation.setCount(1, "$$")
        binding.meowBottonNavigation.show(1)

        database = CashSentryDB.getDB(this)

        GlobalScope.launch {
            database.userRepository().upsertUser(UserProfile("kalu@gmail.com", "admin"))
            Log.i("db","running")
        }

        binding.meowBottonNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> navigateToHomeFragment()
                2 -> navigateToNotificationFragment()
                3 -> navigateToProfileFragment()
            }
        }

    }

    private fun navigateToProfileFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.profileFragment)
    }

    private fun navigateToHomeFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.homeFragment)
    }

    private fun navigateToNotificationFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.notificationFragment)
    }

}