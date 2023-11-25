package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_brands")
data class ProductBrand(
    @PrimaryKey(autoGenerate = true)
    val brandId: Long = 0,
    val brandName: String,
    val brandImage: String
)
