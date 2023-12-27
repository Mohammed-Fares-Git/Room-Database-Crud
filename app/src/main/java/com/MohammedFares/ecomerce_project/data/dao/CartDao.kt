package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert
    suspend fun creat (cart: Cart): Long

    @Update
    suspend fun update (cart: Cart): Int

    @Query("SELECT COUNT(*) as carts_count FROM cart_table WHERE isCheckedOut = 0 AND clientId = :clientId")
    suspend fun getNonCheckedOutCart(clientId: Long): Int

    @Query("SELECT * FROM cart_table WHERE isCheckedOut = 0 AND clientId = :clientId LIMIT 1")
    suspend fun getCurrentCart(clientId: Long): List<Cart>

    @Query("SELECT * FROM cart_table WHERE cartId = :cartId LIMIT 1")
    fun getCartById(cartId: Long): List<Cart>


    @Query("SELECT COUNT(*) as cart_items_cont FROM cart_table as cart JOIN cart_items as item ON cart.cartId = item.cartId WHERE cart.cartId = :cartId")
    fun getCartItemsCount(cartId: Long): Flow<Int>


}