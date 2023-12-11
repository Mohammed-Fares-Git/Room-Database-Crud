package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductSizeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    val productDao: ProductDao,
    val productTypeDao: ProductTypeDao,
    val productBrandDao: ProductBrandDao,
    val productColorDao: ProductColorDao,
    val productSizeDao: ProductSizeDao,
    val clientDao: ClientDao,
    val imagesDao: ProductImagesDao
) :AdminRepository{
    override suspend fun getAllProducts(): List<ProductWithDetails> {
        return productDao.getAllProductsWithDetails()
    }

    override suspend fun getProductById(id: Long): ProductWithDetails {
        return productDao.getAllProductWithDetailsById(id)
    }

    override suspend fun deleteProductImage(productSubImage: ProductSubImage): Int {
        delay(500L)
        return imagesDao.delete(productSubImage)
    }

    override suspend fun deleteProductSize(productSize: ProductSize): Int {
        delay(400L)
        return productSizeDao.delete(productSize)
    }

    override suspend fun deleteProductColor(productColor: ProductColor): Int {
        delay(400L)
        return productColorDao.delete(productColor)
    }

    override suspend fun editProductBrand(productBrand: ProductBrand): Int {
        delay(400L)
        return productBrandDao.update(productBrand)
    }

    override suspend fun editProductType(productType: ProductType): Int {
        delay(400L)
        return productTypeDao.update(productType)
    }

    override suspend fun editProduct(product: Product): Int {
        delay(400L)
        return productDao.update(product)
    }

    override suspend fun editProductSize(productSize: ProductSize): Int {
        delay(400L)
        return productSizeDao.update(productSize)
    }

    override suspend fun addProductSize(productSize: ProductSize): Long {
        delay(400L)
        return productSizeDao.insertSize(productSize)
    }

    override suspend fun editProductColor(productColor: ProductColor): Int {
        delay(400L)
        return productColorDao.update(productColor)
    }

    override suspend fun addProductColor(productColor: ProductColor): Long {
        delay(400L)
        return productColorDao.insertColor(productColor)
    }

    override suspend fun addProductImage(productSubImage: ProductSubImage): Long {
        delay(400L)
        return imagesDao.insertImage(productSubImage)
    }

    override suspend fun editProductImage(productSubImage: ProductSubImage): Int {
        return imagesDao.update(productSubImage)
    }
}