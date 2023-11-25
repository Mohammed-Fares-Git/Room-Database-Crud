package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_types")
data class ProductType(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0,
    val typeName: String,
    val typeImage: String
)
