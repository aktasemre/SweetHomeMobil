package com.sweethome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweethome.adapters.CheckListAdapter
import com.sweethome.databinding.FragmentCheckListBinding
import com.sweethome.viewmodels.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar
import com.sweethome.utils.Result
import android.content.res.Resources

@AndroidEntryPoint
class CheckListFragment : Fragment() {
    private var _binding: FragmentCheckListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckListViewModel by viewModels()
    private val adapter = CheckListAdapter { checkItem ->
        navigateToDetail(checkItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCheckItems()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CheckListFragment.adapter
        }
    }

    private fun observeCheckItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkItems.collect { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        if (result.data.isEmpty()) {
                            showEmptyState(true)
                        } else {
                            showEmptyState(false)
                            adapter.submitList(result.data)
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showError(result.exception)
                    }
                    Result.Loading -> {
                        showLoading(true)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showEmptyState(show: Boolean) {
        binding.emptyText.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showError(error: Exception) {
        Snackbar.make(
            binding.root,
            getString(R.string.error_loading_items, error.message),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.retry)) {
            // Yeniden yükleme işlemi için ViewModel'de bir fonksiyon ekleyebiliriz
            viewModel.refreshItems()
        }.show()
    }

    private fun navigateToDetail(checkItem: CheckItem) {
        val action = CheckListFragmentDirections.actionChecklistToDetail(checkItem)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 