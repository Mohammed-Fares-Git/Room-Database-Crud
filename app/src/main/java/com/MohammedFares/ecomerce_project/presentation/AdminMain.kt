package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
@AndroidEntryPoint
class AdminMain : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        authManager = AuthManager(this)

        val loginState = authManager.isLoggedIn and authManager.isAdminLoggedIn()
        

        if (!loginState) {
            startActivity(Intent(this@AdminMain,LoginMain::class.java))
        }
    }
}