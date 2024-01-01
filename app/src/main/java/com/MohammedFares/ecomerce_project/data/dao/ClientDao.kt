package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client

@Dao
interface ClientDao {

    @Insert
    suspend fun insert (client: Client) : Long

    @Query("SELECT * FROM clients WHERE email = :email AND password = :password")
    suspend fun getClient(email: String, password: String): Client

    @Query("SELECT * FROM clients WHERE clientId = :clientId")
    suspend fun getClientById(clientId: Long): Client
}