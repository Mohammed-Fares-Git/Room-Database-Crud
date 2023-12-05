package com.MohammedFares.ecomerce_project.domain.use_case.comon

import android.content.Context
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val repository: AdminRepository
) {
    operator fun invoke(id: Long): Flow<Resource<ProductWithDetails>> = flow {
        emit(Resource.Loading())
        try {
            val products = repository.getProductById(id)
            if (products != null) {
                emit(Resource.Success(products))
            } else {
                emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
            }

        } catch (e: Exception) {
            emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
        }
    }
}