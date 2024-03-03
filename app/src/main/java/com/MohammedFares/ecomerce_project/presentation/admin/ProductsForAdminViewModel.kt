package com.MohammedFares.ecomerce_project.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.use_case.admin.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsForAdminViewModel @Inject constructor(
    val getProdcts: GetAllProductsUseCase,
    val addProduct: AdminRepository
): ViewModel() {
    private val _productsStateFlow: MutableStateFlow<Resource<List<ProductExpendable>>> = MutableStateFlow(Resource.Empty())
    val prodctsStateFlow: StateFlow<Resource<List<ProductExpendable>>> = _productsStateFlow

    fun getListOfProducts() {
        viewModelScope.launch {
            getProdcts().collect {
                _productsStateFlow.value = it
            }
        }
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        addProduct.addProduct(product)
    }

    init {
        getListOfProducts()
    }
}