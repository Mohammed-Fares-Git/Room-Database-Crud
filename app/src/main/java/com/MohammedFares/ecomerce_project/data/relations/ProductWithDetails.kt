package com.MohammedFares.ecomerce_project.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType

data class ProductWithDetails(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "brandId",
        entityColumn = "brandId"
    )
    val brand: ProductBrand?,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "typeId"
    )
    val type: ProductType?,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val colors: List<ProductColor>,

    @Relation(
    parentColumn = "productId",
    entityColumn = "productId"
    )
    val sizes: List<ProductSize>,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val subImages: List<ProductSubImage>,
)