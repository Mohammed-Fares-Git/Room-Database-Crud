package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductType

interface ClientRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getTypes(): List<ProductType>
    suspend fun getBrands(): List<ProductBrand>
    suspend fun likeProduct(productId: Long, clienttId: Long)
    suspend fun removeLikeProduct(productLike: ProductLike)
    suspend fun getCartItemsCount(cartId: Long): Int
    suspend fun getCarts(): List<Cart>
    suspend fun getCurrentCarts(clientId: Long): Int
    suspend fun creatCart(cart: Cart): Long
    suspend fun currentCart(clientI: Long): List<Cart>
}