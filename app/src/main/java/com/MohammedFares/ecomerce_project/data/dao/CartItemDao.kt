package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Client

@Dao
interface CartItemDao {

    @Insert
    suspend fun addToCart (cartId: Long, productId: Long) : Long
    @Delete
    suspend fun removeFromCart (cartItem: CartItem) : Long
    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    suspend fun getCartItems(cartId: Long): List<CartItem>
}