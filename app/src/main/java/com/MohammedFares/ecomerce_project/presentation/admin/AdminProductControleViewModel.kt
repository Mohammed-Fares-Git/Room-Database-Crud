package com.MohammedFares.ecomerce_project.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminProductControleViewModel @Inject constructor(
    val getProduct: GetProductByIdUseCase,
    val repository: AdminRepository
) : ViewModel() {
    private val _productStateFlow: MutableStateFlow<Resource<ProductWithDetails>> =
        MutableStateFlow(Resource.Empty())
    val productStateFlow: StateFlow<Resource<ProductWithDetails>> = _productStateFlow

    fun getProductById(id: Long) {
        viewModelScope.launch {
            getProduct(id).collect {
                _productStateFlow.value = it
            }
        }
    }


    fun addPeodactImage(id: Long, url: String) = viewModelScope.launch {
        repository.addProductImage(
            ProductSubImage(
                productId = id,
                imageUrl = url
            )
        )
    }

    fun editPeodactImage(productSubImage: ProductSubImage) =
        viewModelScope.launch { repository.editProductImage(productSubImage) }

    fun deletePeodactImage(productSubImage: ProductSubImage) =
        viewModelScope.launch {
            repository.deleteProductImage(productSubImage)
            getProductById(productSubImage.productId)
        }


    fun addProductSize(id: Long, size: String) = viewModelScope.launch {
        repository.addProductSize(
            ProductSize(
                productId = id,
                sizeName = size
            )
        )
    }

    fun editProductSize(productSize: ProductSize) =
        viewModelScope.launch { repository.editProductSize(productSize) }

    fun addProductColor(id: Long, name: String, hex: String) = viewModelScope.launch {
        repository.addProductColor(
            ProductColor(
                productId = id,
                colorName = name,
                colorHexCode = hex
            )
        )
    }

    fun editProductColor(productColor: ProductColor) =
        viewModelScope.launch { repository.editProductColor(productColor) }

    fun editProduct(product: Product) = viewModelScope.launch { repository.editProduct(product) }
    fun editProductType(productType: ProductType) = viewModelScope.launch { repository.editProductType(productType) }
}