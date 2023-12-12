package com.MohammedFares.ecomerce_project.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails

data class ProductsListScreenState(
    var searchParam: String? = null,
    var selctedColor: String? = null,
    var selectedSize: String? = null,
    var maxPrice: Int? = null,
    var freeDelevry: Boolean? = null,
    var promo: Boolean? = null
)
