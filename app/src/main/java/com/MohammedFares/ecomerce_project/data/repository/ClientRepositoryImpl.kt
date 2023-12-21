package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductLikeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    val product: ProductDao,
    val likeDao: ProductLikeDao,
    val type: ProductTypeDao,
    val brand: ProductBrandDao
) :ClientRepository{
    override suspend fun getAllProducts(): List<Product> {
        return emptyList()
    }

    override suspend fun getTypes(): List<ProductType> {
        return type.getAll()
    }

    override suspend fun getBrands(): List<ProductBrand> {
        return brand.getAll()
    }

    override suspend fun likeProduct(productId: Long, clienttId: Long) {
        likeDao.insertLike(ProductLike(productId = productId, clientId = clienttId))
    }

    override suspend fun removeLikeProduct(productLike: ProductLike) {
        likeDao.delete(productLike)
    }


}