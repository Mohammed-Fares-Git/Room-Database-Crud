package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin

@Dao
interface AdminDao {

    @Query("SELECT * FROM admins WHERE email = :email AND password = :password")
    suspend fun getAllAdmins(email: String, password: String): List<Admin>
    @Query("SELECT * FROM admins WHERE email = :email AND password = :password")
    suspend fun getAdmin(email: String, password: String): Admin

    @Insert
    suspend fun insertAdmin(admin: Admin)

    // Add other operations as needed
}