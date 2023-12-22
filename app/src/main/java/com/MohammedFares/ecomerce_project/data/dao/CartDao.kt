package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.Client

@Dao
interface CartDao {

    @Insert
    suspend fun creat (cart: Cart): Long

    @Query("SELECT COUNT(*) as carts_count FROM cart_table WHERE isCheckedOut = 0 AND clientId = :clientId")
    suspend fun getNonCheckedOutCart(clientId: Long): Int


    @Query("SELECT COUNT(*) as cart_items_cont FROM cart_table as cart JOIN cart_items as item ON cart.cartId = item.cartId")
    suspend fun getCartItemsCount(): Int


}