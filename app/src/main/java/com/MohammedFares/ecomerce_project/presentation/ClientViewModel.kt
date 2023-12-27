package com.MohammedFares.ecomerce_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            client.getCartItemsCount(cartId).collect {
                _cartItemsCountStateFlow.value = it
            }
    }
    fun getCurrentCart(clientId: Long, action: (cartId: Long)->Unit): Job = viewModelScope.launch {
        var nonCheckedOutCartsCount = async { client.getCurrentCarts(clientId) }

        if (nonCheckedOutCartsCount.await() < 1) {
            val id = client.creatCart(Cart(clientId = clientId))

            action(id)
            /*
            delay(50L)
            getCurrentCart(clientId) {
                action(it)
            }

             */
        } else {
            val cart = async{ client.currentCart(clientId) }
            action(cart.await()[0].cartId)
        }
    }
}