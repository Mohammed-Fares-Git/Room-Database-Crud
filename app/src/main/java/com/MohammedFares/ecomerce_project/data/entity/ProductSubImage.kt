package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "product_sub_images", foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class ProductSubImage(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = 0,
    val productId: Long,
    val imageUrl: String,
)
