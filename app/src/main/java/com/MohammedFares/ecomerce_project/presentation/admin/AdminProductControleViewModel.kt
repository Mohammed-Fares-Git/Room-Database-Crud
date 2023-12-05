package com.MohammedFares.ecomerce_project.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminProductControleViewModel @Inject constructor(
    val getProduct: GetProductByIdUseCase,
    val repository: AdminRepository
): ViewModel() {
    private val _productStateFlow: MutableStateFlow<Resource<ProductWithDetails>> = MutableStateFlow(Resource.Empty())
    val productStateFlow: StateFlow<Resource<ProductWithDetails>> = _productStateFlow

    fun getProductById(id: Long) {
        viewModelScope.launch {
            getProduct(id).collect {
                _productStateFlow.value = it
            }
        }
    }


    fun addPeodactImage(id: Long,url: String) {
        // tal mn ba3ed
    }

    fun editPeodactImage(productSubImage: ProductSubImage) {
        // tal mn ba3ed
    }

    fun deletePeodactImage(productSubImage: ProductSubImage) {
        viewModelScope.launch {
            repository.deleteProductById(productSubImage)
            getProductById(productSubImage.productId)
        }
    }

    fun addPeodactSize(id: Long,size: String) {
        // tal mn ba3ed
    }

    fun editPeodactImage(productSize: ProductSize) {
        // tal mn ba3ed
    }

    fun addPeodactColor(id: Long,hex: String) {
        // tal mn ba3ed
    }

    fun editPeodactColor(productColor: ProductColor) {
        // tal mn ba3ed
    }

    fun editPeodactMainImage(url: String) {
        // tal mn ba3ed
    }
}