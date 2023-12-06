package com.MohammedFares.ecomerce_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_types")
data class ProductType(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0,
    val typeName: String,
    val typeImage: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductType

        return typeName == other.typeName
    }

    override fun hashCode(): Int {
        return typeName.hashCode()
    }
}
