package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor

@Dao
interface ProductBrandDao {
    @Insert
    suspend fun insertBrand(productBrand: ProductBrand): Long
    @Update
    suspend fun update(productBrand: ProductBrand): Int
}