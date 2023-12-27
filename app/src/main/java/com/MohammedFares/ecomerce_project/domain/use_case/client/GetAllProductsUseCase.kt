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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val repository: AdminRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductWithDetails>>> = flow {
        emit(Resource.Loading())
        try {
            val products = repository.getAllProducts()
            if (products.isNotEmpty()) {
                emit(Resource.Success(products))
            } else {
                //emit(Resource.Error(ctx.getString(R.string.no_products_found)))
                emit(Resource.Error("main product empty"))
            }

        } catch (e: Exception) {
            //emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
            emit(Resource.Error(e.message.toString()))
        }
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }
}