package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize

@Dao
interface ProductLikeDao {
    @Insert
    suspend fun insertLike(productLike: ProductLike): Long

    @Delete
    suspend fun delete(productLike: ProductLike): Int





}