package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductLikeDao {
    @Insert
    suspend fun insertLike(productLike: ProductLike): Long

    @Delete
    suspend fun delete(productLike: ProductLike): Int

    @Query("SELECT COUNT(*) as likes_count FROM product_likes as likes JOIN clients as clients ON likes.clientId = clients.clientId WHERE likes.clientId = :clientId")
    fun getLikesCount(clientId: Long): Flow<Int>


    //@Query("SELECT COUNT(*) as likes_count FROM product_likes WHERE clientId = :clientId")
    //fun getLikesCount(clientId: Long): Flow<Int>



}