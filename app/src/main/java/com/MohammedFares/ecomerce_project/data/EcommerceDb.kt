package com.MohammedFares.ecomerce_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.MohammedFares.ecomerce_project.data.dao.AdminDao
import com.MohammedFares.ecomerce_project.data.dao.CartDao
import com.MohammedFares.ecomerce_project.data.dao.CartItemDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductLikeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductSizeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType

@Database(entities = [
    Admin::class,
    Client::class,
    ProductBrand::class,
    ProductType::class,
    Product::class,
    ProductSize::class,
    ProductSubImage::class,
    ProductColor::class,
    Cart::class,
    CartItem::class,
    ProductLike::class
], version = 1, exportSchema = false)
abstract class EcommerceDb: RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun clientDao(): ClientDao
    abstract fun productColorDao(): ProductColorDao
    abstract fun productSizeDao(): ProductSizeDao
    abstract fun productLikeDao(): ProductLikeDao
    abstract fun productTypeDao(): ProductTypeDao
    abstract fun productBrandDao(): ProductBrandDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun imagetDao(): ProductImagesDao


}