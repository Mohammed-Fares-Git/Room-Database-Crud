package com.MohammedFares.ecomerce_project.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

data class ProductExpendable(
    val productWithDetails: ProductWithDetails,
    var isEpended: Boolean = false
)
