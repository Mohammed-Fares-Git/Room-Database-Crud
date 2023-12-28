package com.MohammedFares.ecomerce_project.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.domain.use_case.client.GetFavoriteProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesPageViewModel @Inject constructor(
    val getFavorites: GetFavoriteProductsUseCase
): ViewModel() {
    private val _FavoritProductStateFlow: MutableStateFlow<Resource<List<ProductWithDetails>>> =
        MutableStateFlow(Resource.Empty())
    val favoriteProductStateFlow: StateFlow<Resource<List<ProductWithDetails>>> = _FavoritProductStateFlow


    fun getProducts(clientId: Long) {
        viewModelScope.launch {
            getFavorites(clientId).collect {
                _FavoritProductStateFlow.value = it
            }
        }
    }
}