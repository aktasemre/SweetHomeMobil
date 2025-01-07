package com.sweethome.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweethome.models.Product
import com.sweethome.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Loading)
    val productsState: StateFlow<ProductsState> = _productsState

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _productsState.value = ProductsState.Loading
            val result = repository.getProducts()
            _productsState.value = when {
                result.isSuccess -> ProductsState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> ProductsState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> ProductsState.Error("Unknown error")
            }
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            _productsState.value = ProductsState.Loading
            val result = repository.getProductsByCategory(category)
            _productsState.value = when {
                result.isSuccess -> ProductsState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> ProductsState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> ProductsState.Error("Unknown error")
            }
        }
    }
}

sealed class ProductsState {
    object Loading : ProductsState()
    data class Success(val products: List<Product>) : ProductsState()
    data class Error(val message: String) : ProductsState()
} 