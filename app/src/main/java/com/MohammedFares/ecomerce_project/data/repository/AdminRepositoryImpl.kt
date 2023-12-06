package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    val product: ProductDao,
    val productType: ProductTypeDao,
    val productBrand: ProductBrandDao,
    val productColorDao: ProductColorDao,
    val clientDao: ClientDao,
    val imagesDao: ProductImagesDao
) :AdminRepository{
    override suspend fun getAllProducts(): List<ProductWithDetails> {
        return product.getAllProductsWithDetails()
    }

    override suspend fun getProductById(id: Long): ProductWithDetails {
        return product.getAllProductWithDetailsById(id)
    }

    override suspend fun deleteProductById(productSubImage: ProductSubImage) {
        imagesDao.delete(productSubImage)
    }
}