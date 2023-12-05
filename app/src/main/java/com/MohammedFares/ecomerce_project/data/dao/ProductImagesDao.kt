package com.MohammedFares.ecomerce_project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage

@Dao
interface ProductImagesDao {
    @Insert
    suspend fun insertImage(image: ProductSubImage):Long

    @Delete
    suspend fun delete(image: ProductSubImage)


}