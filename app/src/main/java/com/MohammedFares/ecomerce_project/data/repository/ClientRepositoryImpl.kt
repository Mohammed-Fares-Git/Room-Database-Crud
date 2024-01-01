package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.CartDao
import com.MohammedFares.ecomerce_project.data.dao.CartItemDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.OrderDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductLikeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Order
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.CartItemDetails
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    val product: ProductDao,
    val likeDao: ProductLikeDao,
    val type: ProductTypeDao,
    val cartProducts: CartDao,
    val cartItemDao: CartItemDao,
    val orderDao: OrderDao,
    val brand: ProductBrandDao
) :ClientRepository{
    override suspend fun getAllProducts(): List<Product> {
        return emptyList()
    }

    override suspend fun getTypes(): List<ProductType> {
        return type.getAll()
    }

    override suspend fun getBrands(): List<ProductBrand> {
        return brand.getAll()
    }

    override suspend fun likeProduct(productId: Long, clienttId: Long) {
        likeDao.insertLike(ProductLike(productId = productId, clientId = clienttId))
    }

    override suspend fun removeLikeProduct(productLike: ProductLike) {
        likeDao.delete(productLike)
    }

    override fun getCartItemsCount(cartId: Long): Flow<Int> {
        return cartProducts.getCartItemsCount(cartId)
    }

    override fun getLikesCount(clientId: Long): Flow<Int> {
        return likeDao.getLikesCount(clientId)
    }

    override suspend fun getCarts(): List<Cart> {
        return emptyList()
    }

    override suspend fun getCartItems(cartId: Long): List<CartItemDetails> {
        return cartItemDao.getCartItems(cartId)
    }


    override suspend fun getCurrentCarts(clientId: Long): Int {
        return cartProducts.getNonCheckedOutCart(clientId)
    }

    override suspend fun creatCart(cart: Cart): Long {
        return cartProducts.creat(cart)
    }

    override suspend fun currentCart(clientId: Long): List<Cart> {
        return cartProducts.getCurrentCart(clientId)
    }

    override suspend fun addToCart(cartItem: CartItem): Long {
        return cartItemDao.addToCart(cartItem)
    }

    override suspend fun removeFromCart(cartItem: CartItem): Int {
        return cartItemDao.removeFromCart(cartItem)
    }

    override suspend fun order(order: Order): Long {
        return orderDao.creat(order)
    }

    override suspend fun updateOrder(order: Order): Int {
        return 0
    }

    override suspend fun getOrdersCount(clientId: Long): Flow<Int> {
        return orderDao.getOrdersCount(clientId)
    }

    override suspend fun getCart(cartId: Long): List<Cart> {
        return cartProducts.getCartById(cartId)
    }

    override suspend fun updateCart(cart: Cart): Int {
        return cartProducts.update(cart)
    }

    override suspend fun getOrders(clientId: Long): List<Order> {
        return orderDao.getAll(clientId)
    }


}