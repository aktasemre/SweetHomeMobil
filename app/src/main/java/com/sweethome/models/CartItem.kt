package com.sweethome.models

data class CartItem(
    val product: Product,
    var quantity: Int = 1
) 