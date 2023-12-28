package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.AdminDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val adminDao: AdminDao,
    val clientDao: ClientDao
) : AuthRepository {
    override suspend fun adminAuth(firstName: String, lastName: String): Admin {
        return adminDao.getAdmin(firstName, lastName)
    }

    override suspend fun clientAuth(firstName: String, lastName: String): Client {
        return clientDao.getClient(firstName, lastName)
    }

    override suspend fun clientSignUp(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        gender: String
    ): Long {
        return clientDao.insert(
            Client(
                firstname = firstName,
                lastname = lastName,
                email = email,
                password = pass,
                gender = gender
            )
        )
    }
}