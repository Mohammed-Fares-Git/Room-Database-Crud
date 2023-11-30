package com.MohammedFares.ecomerce_project.di.module

import com.MohammedFares.ecomerce_project.domain.use_case.admin.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.presentation.admin.ProductsForAdminViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideProductsForAdminViewModel(
        getProducts: GetAllProductsUseCase
    ): ProductsForAdminViewModel {
        return ProductsForAdminViewModel(getProducts)
    }
}