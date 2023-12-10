package com.MohammedFares.ecomerce_project.domain.repository

import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client

interface AuthRepository {
    suspend fun adminAuth(firstName: String, lastName: String): Admin
    suspend fun clientAuth(firstName: String, lastName: String): Client
}