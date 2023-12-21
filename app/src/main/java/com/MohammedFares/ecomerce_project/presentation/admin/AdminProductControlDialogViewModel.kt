package com.MohammedFares.ecomerce_project.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductScreenState
import com.MohammedFares.ecomerce_project.domain.model.ProductsListScreenState
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetAllBrandsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetAllTypesUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetFilteredProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminProductControlDialogViewModel @Inject constructor(
    val types: GetAllTypesUseCase,
    val brands: GetAllBrandsUseCase
) : ViewModel() {


    private val _typesStateFlow: MutableStateFlow<Resource<List<ProductType>>> =
        MutableStateFlow(Resource.Empty())
    val typesStateFlow: StateFlow<Resource<List<ProductType>>> = _typesStateFlow


    private val _brandsStateFlow: MutableStateFlow<Resource<List<ProductBrand>>> =
        MutableStateFlow(Resource.Empty())
    val brandsStateFlow: StateFlow<Resource<List<ProductBrand>>> = _brandsStateFlow
    fun getTypes() {
        viewModelScope.launch {
            brands().collect {
                _brandsStateFlow.value = it
            }
        }
    }

    fun getBrandss() {
        viewModelScope.launch {
            types().collect {
                _typesStateFlow.value = it
            }
        }
    }

    init {
        getTypes()
        getBrandss()
    }


}