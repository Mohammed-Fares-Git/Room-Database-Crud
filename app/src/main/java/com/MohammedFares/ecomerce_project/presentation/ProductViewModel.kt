package com.MohammedFares.ecomerce_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.model.ProductScreenState
import com.MohammedFares.ecomerce_project.domain.use_case.admin.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val getProdct: GetProductByIdUseCase
): ViewModel() {
    private val _productsStateFlow: MutableStateFlow<Resource<ProductWithDetails>> = MutableStateFlow(Resource.Empty())
    val prodctsStateFlow: StateFlow<Resource<ProductWithDetails>> = _productsStateFlow



    private val _productScreenState: MutableStateFlow<ProductScreenState> = MutableStateFlow(ProductScreenState())
    val productScreenState: StateFlow<ProductScreenState> = _productScreenState
    fun getProduct(id: Long) {
        viewModelScope.launch {
            getProdct(id).collect {
                _productsStateFlow.value = it
            }
        }
    }

    fun selectColor(id: Long) {
        viewModelScope.launch {
            _productScreenState.value = productScreenState.value.copy(
                selctedColor = id
            )
        }

    }

    fun selectSize(id: Long) {
        _productScreenState.value = productScreenState.value.copy(
            selectedSize = id
        )
    }


}