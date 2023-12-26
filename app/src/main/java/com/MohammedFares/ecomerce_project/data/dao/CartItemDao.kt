package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.data.relations.CartItemDetails

@Dao
interface CartItemDao {

    @Insert
    suspend fun addToCart (cartItem: CartItem) : Long
    @Delete
    suspend fun removeFromCart (cartItem: CartItem) : Int

    @Update
    suspend fun update (cartItem: CartItem) : Int
    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    suspend fun getCartItems(cartId: Long): List<CartItemDetails>

}