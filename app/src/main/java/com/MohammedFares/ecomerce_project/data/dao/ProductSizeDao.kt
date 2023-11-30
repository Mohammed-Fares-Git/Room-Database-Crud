package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize

@Dao
interface ProductSizeDao {
    @Insert
    suspend fun insertSize(productSize: ProductSize):Long


}