package com.MohammedFares.ecomerce_project.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import com.MohammedFares.ecomerce_project.domain.use_case.auth.ClientAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientLoginViewModel @Inject constructor(
    val auth: ClientAuthUseCase
): ViewModel() {
    private val _authStateFlow: MutableStateFlow<Resource<Client>> = MutableStateFlow(Resource.Empty())
    val authStateFlow: StateFlow<Resource<Client>> = _authStateFlow

    fun checkClient(firstName: String, lastName: String) {
        viewModelScope.launch {
            auth(firstName,lastName).collect {
                _authStateFlow.value = it
            }
        }
    }
}