package com.sweethome.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sweethome.databinding.FragmentProductsBinding
import com.sweethome.models.Product
import com.sweethome.adapters.ProductAdapter
import com.sweethome.activities.ProductDetailActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    
    private val productsList = mutableListOf<Product>()
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ProductAdapter(productsList) { product ->
                // Ürün detay sayfasına yönlendir
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("product", product)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsState.collectLatest { state ->
                when (state) {
                    is ProductsState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.productsRecyclerView.visibility = View.GONE
                    }
                    is ProductsState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productsRecyclerView.visibility = View.VISIBLE
                        productsList.clear()
                        productsList.addAll(state.products)
                        binding.productsRecyclerView.adapter?.notifyDataSetChanged()
                    }
                    is ProductsState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productsRecyclerView.visibility = View.VISIBLE
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 