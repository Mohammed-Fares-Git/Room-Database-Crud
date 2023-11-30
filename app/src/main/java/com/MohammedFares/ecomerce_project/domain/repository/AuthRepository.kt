package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Admin

interface AuthRepository {
    suspend fun adminAuth(firstName: String, lastName: String): Admin
}