package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MohammedFares.ecomerce_project.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class ClientMian : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_mian)



        val loginState = false

        if (!loginState) {
            startActivity(Intent(this@ClientMian,LoginMain::class.java))
        }
    }
}