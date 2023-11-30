package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "product_colors", foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class ProductColor(
    @PrimaryKey(autoGenerate = true)
    val colorId: Long = 0,
    val productId: Long,
    val colorName: String,
    val colorHexCode: String
)
