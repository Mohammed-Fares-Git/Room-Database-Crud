package com.MohammedFares.ecomerce_project.domain.use_case.client

import android.content.Context
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val repository: AdminRepository
) {
    operator fun invoke(searchParam: String = "", delevry: Boolean = false, gender: String? = null, type: String? = null, maxPrice: Int? = null, minPrice: Int? = null): Flow<Resource<List<ProductWithDetails>>> = flow {
        emit(Resource.Loading())
        try {

            var products = repository.getAllProducts()

            if (delevry) {
                products = products.filter {productWithDetails->
                    productWithDetails.product.livreson == delevry
                }
            }

            type?.let {
                products = products.filter {productWithDetails->
                    productWithDetails.type?.typeName == type
                }
            }

            gender?.let {
                products = products.filter {productWithDetails->
                    productWithDetails.product.gender == gender
                }
            }

            maxPrice?.let {
                products = products.filter {productWithDetails ->
                    productWithDetails.product.price <= maxPrice
                }
            }

            minPrice?.let {
                products = products.filter {productWithDetails ->
                    productWithDetails.product.price >= minPrice
                }
            }

            if (products.isNotEmpty()) {
                emit(Resource.Success(products))
            } else {
                emit(Resource.Error(ctx.getString(R.string.no_products_found)))
            }

        } catch (e: Exception) {
            emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
        }
    }
}