package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize

@Dao
interface ProductSizeDao {
    @Insert
    suspend fun insertSize(productSize: ProductSize): Long

    @Delete
    suspend fun delete(productSize: ProductSize): Int

    @Update
    suspend fun update(productSize: ProductSize): Int



}