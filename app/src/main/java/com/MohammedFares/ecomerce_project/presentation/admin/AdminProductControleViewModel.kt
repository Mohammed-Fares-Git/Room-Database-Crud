package com.MohammedFares.ecomerce_project.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AdminRepository
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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


    fun addProdactImage(productSubImage: ProductSubImage) = viewModelScope.launch {
            repository.addProductImage(productSubImage)
            delay(500L)
            getProductById(productSubImage.productId)

    }

    fun editPeodactImage(productSubImage: ProductSubImage) =
        viewModelScope.launch {
            repository.editProductImage(productSubImage)
            delay(500L)
            getProductById(productSubImage.productId)
        }

    fun deletePeodactImage(productSubImage: ProductSubImage) =
        viewModelScope.launch {
            repository.deleteProductImage(productSubImage)
            getProductById(productSubImage.productId)
        }


    fun addProductSize(size: ProductSize) = viewModelScope.launch {
        repository.addProductSize(
            size
        )
        delay(500L)
        getProductById(size.productId)
    }

    fun editProductSize(productSize: ProductSize) =
        viewModelScope.launch { repository.editProductSize(productSize) }

    fun addProductColor(color: ProductColor) = viewModelScope.launch {
        repository.addProductColor(color)
        delay(500L)
        getProductById(color.productId)
    }

    fun editProductColor(productColor: ProductColor) =
        viewModelScope.launch { repository.editProductColor(productColor) }

    fun editProduct(product: Product) = viewModelScope.launch {
        repository.editProduct(product)
        delay(500L)
        getProductById(product.productId)
    }
    fun editProductType(productType: ProductType) = viewModelScope.launch { repository.editProductType(productType) }
    fun editProductBrand(productBrand: ProductBrand) = viewModelScope.launch { repository.editProductBrand(productBrand) }
}