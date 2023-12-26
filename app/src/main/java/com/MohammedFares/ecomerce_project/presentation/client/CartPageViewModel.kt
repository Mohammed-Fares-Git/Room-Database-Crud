package com.MohammedFares.ecomerce_project.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.relations.CartItemDetails
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.domain.model.ProductScreenState
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import com.MohammedFares.ecomerce_project.domain.use_case.admin.GetAllProductsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetCartItemsUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.comon.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(
    val getCartItemsUseCase: GetCartItemsUseCase,
    val clientRepository: ClientRepository
): ViewModel() {
    private val _ItemesStateFlow: MutableStateFlow<Resource<List<CartItemDetails>>> = MutableStateFlow(Resource.Success(
        emptyList()
    ))
    val itemesStateFlow: StateFlow<Resource<List<CartItemDetails>>> = _ItemesStateFlow


    private val _cartItemsCountStateFlow = MutableStateFlow<Int>(0)
    val cartItemsCountStateFlow: StateFlow<Int> = _cartItemsCountStateFlow

    fun getCartItemsCount(cartId: Long) = viewModelScope.launch {
        clientRepository.getCartItemsCount(cartId).collect {
            _cartItemsCountStateFlow.value = it
        }
    }


    fun getProduct(id: Long) {
        viewModelScope.launch {
            getCartItemsUseCase(id).collect {
                _ItemesStateFlow.value = it
            }
        }
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

    fun moreOfThat(cartItem: CartItem) {
        viewModelScope.launch {

            getProduct(cartItem.cartId)
        }
    }

    fun lessOfThat(cartItem: CartItem) {
        viewModelScope.launch {

            getProduct(cartItem.cartItemId)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            clientRepository.removeFromCart(cartItem)
            getProduct(cartItem.cartId)
        }
    }






}