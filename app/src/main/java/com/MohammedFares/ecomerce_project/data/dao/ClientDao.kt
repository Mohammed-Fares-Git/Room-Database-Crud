package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients")
    suspend fun getAllClients(): List<Client>

    @Query("SELECT * FROM clients WHERE clientId = :clientId")
    suspend fun getClientById(clientId: Long): Client?

    @Insert
    suspend fun insertClient(client: Client)
}