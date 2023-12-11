package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductColor

@Dao
interface ProductColorDao {
    @Insert
    suspend fun insertColor(productColor: ProductColor): Long
    @Update
    suspend fun update(productColor: ProductColor): Int

    @Delete
    suspend fun delete(productColor: ProductColor): Int
}