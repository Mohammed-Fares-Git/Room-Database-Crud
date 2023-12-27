package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.data.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    suspend fun creat (order: Order): Long

    @Query("SELECT COUNT(*) as orders_count FROM orders WHERE clientId = :clienId")
    fun getOrdersCount(clienId: Long): Flow<Int>

}