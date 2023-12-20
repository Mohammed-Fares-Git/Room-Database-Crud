package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "product_sizess", foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class ProductSize(
    @PrimaryKey(autoGenerate = true)
    val sizeId: Long = 0,
    val productId: Long,
    var sizeName: String,
)
