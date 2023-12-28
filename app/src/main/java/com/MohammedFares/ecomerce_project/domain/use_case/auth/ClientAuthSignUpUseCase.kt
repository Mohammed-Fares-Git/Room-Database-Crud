package com.MohammedFares.ecomerce_project.domain.use_case.auth

import android.content.Context
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClientAuthSignUpUseCase @Inject constructor(
    @ApplicationContext val ctx: Context,
    val authRepository: AuthRepository
) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        gender: String
    ): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())
        delay(500L)
        try {
            val clientId = authRepository.clientSignUp(firstName, lastName, email, pass, gender)
            if (clientId.toInt() != -1) {
                emit(Resource.Success(clientId))
            } else {
                emit(Resource.Error("email or password is incorect"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            //emit(Resource.Error(ctx.getString(R.string.unknown_problem)))
        }
    }
}