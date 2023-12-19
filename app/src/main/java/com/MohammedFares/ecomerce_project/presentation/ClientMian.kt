package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.databinding.ActivityClientMianBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
@AndroidEntryPoint
class ClientMian : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var binding: ActivityClientMianBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientMianBinding.inflate(layoutInflater)
        setContentView(binding.root)





        authManager = AuthManager(this)

        val loginState = authManager.isLoggedIn and authManager.isClientLoggedIn()


        if (!loginState) {
            startActivity(Intent(this@ClientMian,LoginMain::class.java))
            finish()
        } else {
            val navController = Navigation.findNavController(this, R.id.client_main_container);
            NavigationUI.setupWithNavController(binding.bnvMain, navController);
        }

    }
}