package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(tableName = "cart_items", foreignKeys = [
    ForeignKey(
        entity = Cart::class,
        parentColumns = ["cartId"],
        childColumns = ["cartId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cartItemId: Long = 0,
    val cartId: Long,
    val productId: Long,
    val quantity: Int
)