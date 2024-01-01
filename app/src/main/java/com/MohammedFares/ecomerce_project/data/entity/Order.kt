package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "orders", foreignKeys = [
    ForeignKey(
        entity = Client::class,
        parentColumns = ["clientId"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Cart::class,
        parentColumns = ["cartId"],
        childColumns = ["cartId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val clientId: Long,
    val cartId: Long,
    var totalPrice: Double = 0.0,
    var state: String = "Procicing",
    val orderDate: Long
)