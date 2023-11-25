package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admins")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val adminId: Long = 0,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
)