package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    val product: ProductDao,
    val productType: ProductTypeDao,
    val productBrand: ProductBrandDao,
    val productColorDao: ProductColorDao,
    val clientDao: ClientDao
) :AdminRepository{
    override suspend fun getAllProducts(): List<ProductWithDetails> {
        return product.getAllProductsWithDetails()
    }
}