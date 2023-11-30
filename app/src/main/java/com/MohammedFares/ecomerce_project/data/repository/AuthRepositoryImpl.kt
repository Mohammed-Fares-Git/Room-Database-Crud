package com.MohammedFares.ecomerce_project.data.repository

import com.MohammedFares.ecomerce_project.data.dao.AdminDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val adminDao: AdminDao,
)  : AuthRepository {
    override suspend fun adminAuth(firstName: String, lastName: String): Admin {
        return adminDao.getAdmin(firstName,lastName)
    }
}