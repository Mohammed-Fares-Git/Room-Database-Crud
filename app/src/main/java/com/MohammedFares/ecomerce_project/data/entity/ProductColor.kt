package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_colors")
data class ProductColor(
    @PrimaryKey(autoGenerate = true)
    val colorId: Long = 0,
    val colorName: String,
    val colorHexCode: String
)
