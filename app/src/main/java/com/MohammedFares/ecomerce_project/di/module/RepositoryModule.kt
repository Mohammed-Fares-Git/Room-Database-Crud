package com.MohammedFares.ecomerce_project.di.module

import com.MohammedFares.ecomerce_project.data.dao.AdminDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.repository.AdminRepositoryImpl
import com.MohammedFares.ecomerce_project.data.repository.AuthRepositoryImpl
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(adminDao: AdminDao): AuthRepository {
        return AuthRepositoryImpl(adminDao)
    }

    @Singleton
    @Provides
    fun provideAdminRepository(productDao: ProductDao, productBrandDao: ProductBrandDao, productColorDao: ProductColorDao, productType: ProductTypeDao, clientDao: ClientDao): AdminRepository {
        return AdminRepositoryImpl(productDao,productType,productBrandDao,productColorDao,clientDao)
    }

}