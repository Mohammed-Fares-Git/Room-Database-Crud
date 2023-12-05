package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

interface AdminRepository {

    suspend fun getAllProducts(): List<ProductWithDetails>
    suspend fun getProductById(id: Long): ProductWithDetails
    suspend fun deleteProductById(productSubImage: ProductSubImage)
}