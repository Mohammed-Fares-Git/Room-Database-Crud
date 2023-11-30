package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "products", foreignKeys = [
    ForeignKey(
        entity = ProductBrand::class,
        parentColumns = ["brandId"],
        childColumns = ["brandId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = ProductType::class,
        parentColumns = ["typeId"],
        childColumns = ["typeId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val mainImage: String,
    val brandId: Long? = null,
    val typeId: Long? = null
)