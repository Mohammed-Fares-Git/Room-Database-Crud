package com.MohammedFares.ecomerce_project.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetAllTypesUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    val getProduct: GetAllProductsUseCase,
    val types: GetAllTypesUseCase
): ViewModel() {
    private val _productStateFlow: MutableStateFlow<Resource<List<ProductWithDetails>>> = MutableStateFlow(Resource.Empty())
    val productStateFlow: StateFlow<Resource<List<ProductWithDetails>>> = _productStateFlow

    private val _typesStateFlow: MutableStateFlow<Resource<List<ProductType>>> = MutableStateFlow(Resource.Empty())
    val typesStateFlow: StateFlow<Resource<List<ProductType>>> = _typesStateFlow

    fun getProducts() {
        viewModelScope.launch {
            getProduct().collect {
                _productStateFlow.value = it
            }
        }
    }

    fun getTypes() {
        viewModelScope.launch {
            types().collect {
                _typesStateFlow.value = it
            }
        }
    }

    init {
        getProducts()
        getTypes()
    }



}