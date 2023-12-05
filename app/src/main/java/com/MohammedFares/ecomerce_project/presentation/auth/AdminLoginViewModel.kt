package com.MohammedFares.ecomerce_project.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.domain.use_case.auth.AdminAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    val auth: AdminAuthUseCase
): ViewModel() {
    private val _authStateFlow: MutableStateFlow<Resource<Admin>> = MutableStateFlow(Resource.Empty())
    val authStateFlow: StateFlow<Resource<Admin>> = _authStateFlow

    fun checkAdmin(firstName: String, lastName: String) {
        viewModelScope.launch {
            auth(firstName,lastName).collect {
                _authStateFlow.value = it
            }
        }
    }
}