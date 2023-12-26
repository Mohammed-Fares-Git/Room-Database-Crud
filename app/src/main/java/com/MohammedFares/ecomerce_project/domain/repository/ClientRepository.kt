package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.CartItemDetails
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getTypes(): List<ProductType>
    suspend fun getBrands(): List<ProductBrand>
    suspend fun likeProduct(productId: Long, clienttId: Long)
    suspend fun removeLikeProduct(productLike: ProductLike)
    fun getCartItemsCount(cartId: Long): Flow<Int>
    suspend fun getCarts(): List<Cart>
    suspend fun getCartItems(cartId: Long): List<CartItemDetails>
    suspend fun getCurrentCarts(clientId: Long): Int
    suspend fun creatCart(cart: Cart): Long
    suspend fun currentCart(clientI: Long): List<Cart>
    suspend fun addToCart(cartItem: CartItem): Long
    suspend fun removeFromCart(cartItem: CartItem): Int
}