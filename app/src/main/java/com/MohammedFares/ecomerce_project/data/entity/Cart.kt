package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table", foreignKeys = [
    ForeignKey(
        entity = Client::class,
        parentColumns = ["clientId"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId: Long = 0,
    val clientId: Long,
    val createdAt: Long,
    var isCheckedOut: Boolean = false
)