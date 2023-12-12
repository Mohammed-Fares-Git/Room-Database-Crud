package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.MohammedFares.ecomerce_project.comon.Constantes

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
    val productDesc: String,
    val gender: String = Constantes.ALL_KEY,
    val price: Double,
    val sold: Int = 0,
    val livreson: Boolean = false,
    val quantity: Int,
    val mainImage: String,
    val brandId: Long? = null,
    val typeId: Long? = null
)