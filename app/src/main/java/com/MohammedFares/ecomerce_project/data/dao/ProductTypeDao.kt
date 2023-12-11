package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductType

@Dao
interface ProductTypeDao {
    @Insert
    suspend fun insertType(productType: ProductType):Long


    @Query("SELECT * FROM product_types")
    suspend fun getAll(): List<ProductType>

    @Update
    suspend fun update(productType: ProductType): Int

}