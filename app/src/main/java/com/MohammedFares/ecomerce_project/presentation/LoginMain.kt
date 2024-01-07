package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMain : AppCompatActivity() {

    private lateinit var authManager: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)


        authManager = AuthManager(this)

        val isClientLoggedIn = authManager.isLoggedIn && authManager.isClientLoggedIn()
        val isAdminLoggedIn = authManager.isLoggedIn && authManager.isAdminLoggedIn()


        if (isClientLoggedIn) {
            startActivity(Intent(this@LoginMain,ClientMian::class.java))
            finish()
        } else if (isAdminLoggedIn) {
            Intent(this@LoginMain,ClientMian::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

}