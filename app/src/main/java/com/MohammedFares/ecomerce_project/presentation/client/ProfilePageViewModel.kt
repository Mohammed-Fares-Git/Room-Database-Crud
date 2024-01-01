package com.MohammedFares.ecomerce_project.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetFavoriteProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    val auth: AuthRepository
): ViewModel() {
    private val _ClientStateFlow: MutableStateFlow<Client?> =
        MutableStateFlow(null)
    val clientStateFlow: StateFlow<Client?> = _ClientStateFlow


    fun getClient(clientId: Long) {
        viewModelScope.launch {
            val client = async { auth.clientAuthById(clientId) }.await()
            _ClientStateFlow.value = client
        }
    }
}