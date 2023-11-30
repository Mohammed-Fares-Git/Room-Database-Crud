package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product): Long

    @Query("SELECT * FROM products")
    suspend fun getAllProduct(): List<Product>
    @Query("SELECT * FROM products WHERE productId = :productId")
    suspend fun getProductById(productId: Long): Product

    @Transaction
    @Query("SELECT * FROM products")
    suspend fun getAllProductsWithDetails(): List<ProductWithDetails>


    @Transaction
    @Query("SELECT * FROM products WHERE productId = :id")
    suspend fun getAllProductWithDetailsById(id: Long): ProductWithDetails
}