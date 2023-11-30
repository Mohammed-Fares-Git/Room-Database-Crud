package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

interface AdminRepository {

    suspend fun getAllProducts(): List<ProductWithDetails>
}