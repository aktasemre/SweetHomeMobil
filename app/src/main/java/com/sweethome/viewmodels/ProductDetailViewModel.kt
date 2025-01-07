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
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productState: StateFlow<ProductDetailState> = _productState

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _productState.value = ProductDetailState.Loading
            val result = repository.getProductById(productId)
            _productState.value = when {
                result.isSuccess -> ProductDetailState.Success(result.getOrNull()!!)
                result.isFailure -> ProductDetailState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> ProductDetailState.Error("Unknown error")
            }
        }
    }
}

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val product: Product) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
} 