package com.MohammedFares.ecomerce_project.domain.use_case.client

import android.content.Context
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBrandsUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val repository: ClientRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductBrand>>> = flow {
        emit(Resource.Loading())
        try {
            var products = repository.getBrands()
            val productsSet = products.toSet()

            products = productsSet.toList()
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