package com.MohammedFares.ecomerce_project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MohammedFares.ecomerce_project.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
    }
}