package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.MohammedFares.ecomerce_project.comon.Constants

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val clientId: Long = 0,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val profileImage: String = "",
    val state: String = Constants.ACTIVE_CLIENT,
    val gender: String
)
