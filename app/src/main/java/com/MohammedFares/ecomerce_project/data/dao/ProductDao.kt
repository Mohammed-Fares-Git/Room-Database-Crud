package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProduct(): List<Product>
    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductById(productId: Long): Product
}