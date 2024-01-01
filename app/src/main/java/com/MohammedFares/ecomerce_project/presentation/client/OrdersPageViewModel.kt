package com.MohammedFares.ecomerce_project.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.data.entity.Order
import com.MohammedFares.ecomerce_project.domain.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersPageViewModel @Inject constructor(
    val client: ClientRepository
): ViewModel() {
    private val _OrdersStateFlow: MutableStateFlow<List<Order>> =
        MutableStateFlow(emptyList())
    val ordersStateFlow: StateFlow<List<Order>> = _OrdersStateFlow


    fun getOrders(clientId: Long) {
        viewModelScope.launch {
            val orders = async { client.getOrders(clientId) }.await()
            _OrdersStateFlow.value = orders
        }
    }
}