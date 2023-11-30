package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.MohammedFares.ecomerce_project.data.entity.ProductColor

@Dao
interface ProductColorDao {
    @Insert
    suspend fun insertColor(productColor: ProductColor):Long
}