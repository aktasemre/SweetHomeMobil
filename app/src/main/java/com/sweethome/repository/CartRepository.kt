package com.sweethome.repository

import com.sweethome.models.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(cartItem: CartItem) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == cartItem.product.id }
        
        if (existingItem != null) {
            existingItem.quantity += cartItem.quantity
        } else {
            currentItems.add(cartItem)
        }
        
        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.removeAll { it.product.id == productId }
        _cartItems.value = currentItems
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val currentItems = _cartItems.value.toMutableList()
        val item = currentItems.find { it.product.id == productId }
        item?.quantity = quantity
        _cartItems.value = currentItems
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }
} 