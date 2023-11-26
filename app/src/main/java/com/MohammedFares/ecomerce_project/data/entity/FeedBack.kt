package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "reviews", foreignKeys = [
    ForeignKey(
        entity = Client::class,
        parentColumns = ["clientId"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class FeedBack(
    @PrimaryKey(autoGenerate = true)
    val reviewId: Long = 0,
    val clientId: Long,
    val productId: Long,
    val feedBackText: String,
    val rating: Int = 0,
    val feedBackType: String
)