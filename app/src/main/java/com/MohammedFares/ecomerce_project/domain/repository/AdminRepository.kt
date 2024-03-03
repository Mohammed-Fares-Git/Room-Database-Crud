package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

interface AdminRepository {

    suspend fun getAllProducts(): List<ProductWithDetails>
    suspend fun getProductById(id: Long): ProductWithDetails
    suspend fun deleteProductImage(productSubImage: ProductSubImage): Int
    suspend fun deleteProductSize(productSize: ProductSize): Int
    suspend fun deleteProductColor(productColor: ProductColor): Int
    suspend fun editProductBrand(productBrand: ProductBrand): Int
    suspend fun editProductType(productType: ProductType): Int
    suspend fun editProduct(product: Product): Int
    suspend fun addProduct(product: Product)
    suspend fun editProductSize(productSize: ProductSize): Int
    suspend fun addProductSize(productSize: ProductSize): Long
    suspend fun editProductColor(productColor: ProductColor): Int
    suspend fun addProductColor(productColor: ProductColor): Long
    suspend fun addProductImage(productSubImage: ProductSubImage): Long
    suspend fun editProductImage(productSubImage: ProductSubImage): Int

}