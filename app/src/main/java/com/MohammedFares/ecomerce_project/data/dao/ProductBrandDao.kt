package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductType

@Dao
interface ProductBrandDao {
    @Insert
    suspend fun insertBrand(productBrand: ProductBrand): Long
    @Update
    suspend fun update(productBrand: ProductBrand): Int

    @Query("SELECT * FROM product_brands")
    suspend fun getAll(): List<ProductBrand>
}