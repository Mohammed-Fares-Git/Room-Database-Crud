package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

interface ClientRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getTypes(): List<ProductType>
    suspend fun likeProduct(productId: Long, clienttId: Long)
    suspend fun removeLikeProduct(productLike: ProductLike)
}