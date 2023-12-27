package com.MohammedFares.ecomerce_project.domain.use_case.client

import android.content.Context
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.model.ProductScreenState
import com.MohammedFares.ecomerce_project.domain.model.ProductsListScreenState
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val repository: AdminRepository
) {
    operator fun invoke(
        state: ProductsListScreenState
    ): Flow<Resource<List<ProductWithDetails>>> = flow {
        emit(Resource.Loading())
        try {

            val products = repository.getAllProducts()
            var fiteredProducts = emptyList<ProductWithDetails>()


            state.freeDelevry?.let {
                if (it) {
                    fiteredProducts = products.filter { productWithDetails ->
                        productWithDetails.product.livreson == true
                    }
                } else {
                    fiteredProducts = products.filter { productWithDetails ->
                        productWithDetails.product.livreson == false || productWithDetails.product.livreson == null
                    }
                }
            }

            state.promo?.let {
                if (it) {
                    fiteredProducts = products.filter { productWithDetails ->
                        productWithDetails.product.sold > 0
                    }
                }
            }


            state.selctedType?.let {
                fiteredProducts = products.filter { productWithDetails ->
                    productWithDetails.type?.typeName == it
                }
            }

            state.selctedGender?.let {
                fiteredProducts = products.filter { productWithDetails ->
                    productWithDetails.product.gender == it
                }
            }

            state.maxPrice?.let {
                fiteredProducts = products.filter { productWithDetails ->
                    productWithDetails.product.price <= it
                }
            }

            state.minPrice?.let {
                fiteredProducts = products.filter { productWithDetails ->
                    productWithDetails.product.price >= it
                }
            }


            if (fiteredProducts.isNotEmpty() && fiteredProducts.size > 0) {
                emit(Resource.Success(fiteredProducts))
            } else {
                //emit(Resource.Error(ctx.getString(R.string.no_products_found)))
                emit(Resource.Error("filter empty"))
            }

        } catch (e: Exception) {
            //emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
            emit(Resource.Error(e.message.toString()))
        }
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }
}