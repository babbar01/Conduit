package com.example.conduit.ui

import android.app.ActionBar
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.api.models.entity.User
import com.example.conduit.R
import com.example.conduit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        const val PREFS_FILE_AUTH = "com.example.conduit.PREFERENCE_FILE_KEY"
        const val PREFS_TOKEN_KEY = "token"
    }

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel : AuthViewModel
    private lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.let { setActionBarColor(it) }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val navView: BottomNavigationView = binding.navView

        Log.d(PREFS_TOKEN_KEY, "onCreate: before accessing shared prefs")

        sharedPreferences = getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)
        sharedPreferences.getString(PREFS_TOKEN_KEY,null)?.let {token->
            authViewModel.getCurrentUser(token)
        }

        authViewModel.user.observe(this) {user->
            updateBottomNavMenu(navView,user)
            Log.d(PREFS_TOKEN_KEY, "onCreate: after shared prefs access")
            user?.let {
                sharedPreferences.edit {
                    putString(PREFS_TOKEN_KEY,user.token)
                }
            } ?: run {
                    sharedPreferences.getString(PREFS_TOKEN_KEY,null)?.let {
                        sharedPreferences.edit{
                            remove(PREFS_TOKEN_KEY)
                        }

                }
            }

        }



        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // TODO: 27-04-2021 check what is the purpose behind these commmented code

//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home,
//            R.id.navigation_create_article,
//            R.id.navigation_profile,
//            )
//        )

        setupActionBarWithNavController(navController)

        navView.setupWithNavController(navController)
    }

    private fun setActionBarColor(actionBar: ActionBar) {

        actionBar.setBackgroundDrawable(R.color.conduit_green.toDrawable())

    }

    private fun updateBottomNavMenu(navView: BottomNavigationView,user : User?) {
        when {
            user == null -> {
                navView.menu.clear()
                navView.inflateMenu(R.menu.bottom_nav_menu_guest)
            }
            else -> {
                navView.menu.clear()
                navView.inflateMenu(R.menu.bottom_nav_menu_user)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}