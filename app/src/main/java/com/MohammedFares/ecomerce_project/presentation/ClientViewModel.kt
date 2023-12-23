package com.MohammedFares.ecomerce_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    val client: ClientRepository
) : ViewModel() {


    private val _cartItemsCountStateFlow = MutableStateFlow<Int>(0)
    val cartItemsCountStateFlow: StateFlow<Int> = _cartItemsCountStateFlow

    fun getCartItemsCount(cartId: Long) = viewModelScope.launch {
        _cartItemsCountStateFlow.value = async {
            client.getCartItemsCount(cartId)
        }.await()
    }
    fun getCurrentCart(clientId: Long, action: (cart: Cart)->Unit): Job = viewModelScope.launch {
        var nonCheckedOutCartsCount = async { client.getCurrentCarts(clientId) }

        if (nonCheckedOutCartsCount.await() < 1) {
            client.creatCart(Cart(clientId = clientId))
            delay(500L)
            getCurrentCart(clientId) {
                action(it)
            }
        } else {
            val cart = async{ client.currentCart(clientId) }
            action(cart.await()[0])
        }
    }
}