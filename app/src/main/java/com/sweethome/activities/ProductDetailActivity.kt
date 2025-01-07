package com.sweethome.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sweethome.databinding.ActivityProductDetailBinding
import com.sweethome.models.Product
import com.sweethome.viewmodels.ProductDetailViewModel
import com.sweethome.viewmodels.ProductDetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("product")
        product?.let { 
            setupUI(it)
            viewModel.loadProduct(it.id)
        }

        setupToolbar()
        observeProduct()
    }

    private fun observeProduct() {
        lifecycleScope.launch {
            viewModel.productState.collectLatest { state ->
                when (state) {
                    is ProductDetailState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ProductDetailState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setupUI(state.product)
                    }
                    is ProductDetailState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupUI(product: Product) {
        binding.apply {
            productName.text = product.name
            productDescription.text = product.description
            productPrice.text = "₺${product.price}"
            productCategory.text = product.category

            Glide.with(this@ProductDetailActivity)
                .load(product.imageUrl)
                .into(productImage)

            addToCartButton.setOnClickListener {
                // TODO: Sepete ekleme işlemi
            }
        }
    }
} 