package com.MohammedFares.ecomerce_project.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType

data class CartItemDetails(
    @Embedded val cartItem: CartItem,

    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val product: Product,

    @Relation(
        parentColumn = "colorId",
        entityColumn = "colorId"
    )
    val color: ProductColor,

    @Relation(
    parentColumn = "sizeId",
    entityColumn = "sizeId"
    )
    val size: ProductSize,


    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val likes: List<ProductLike>
)