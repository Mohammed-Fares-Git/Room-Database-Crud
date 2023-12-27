package com.MohammedFares.ecomerce_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.model.ProductScreenState
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
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
    val getProdct: GetProductByIdUseCase,
    val clientRepository: ClientRepository
): ViewModel() {
    private val _productsStateFlow: MutableStateFlow<Resource<ProductWithDetails>> = MutableStateFlow(Resource.Empty())
    val prodctsStateFlow: StateFlow<Resource<ProductWithDetails>> = _productsStateFlow



    private val _productScreenState: MutableStateFlow<ProductScreenState> = MutableStateFlow(ProductScreenState())
    val productScreenState: StateFlow<ProductScreenState> = _productScreenState
    fun getProduct(id: Long) {
        viewModelScope.launch {
            getProdct(id).collect {
                _productsStateFlow.value = it
                it.data?.let {
                    if (it.colors.size > 0)
                        selectColor(it.colors[0].colorId)
                    if (it.sizes.size > 0)
                        selectSize(it.sizes[0].sizeId)
                }
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

    fun putLike(productId: Long, clientId: Long) {
        viewModelScope.launch {
            clientRepository.likeProduct(productId, clientId)
            getProduct(productId)
        }
    }

    fun removeLike(productLike: ProductLike) {
        viewModelScope.launch {
            clientRepository.removeLikeProduct(productLike)
            getProduct(productLike.productId)
        }
    }

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            clientRepository.addToCart(cartItem)
        }
    }




}