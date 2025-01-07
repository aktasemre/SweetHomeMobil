package com.sweethome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sweethome.databinding.FragmentCheckItemDetailBinding
import com.sweethome.viewmodels.CheckItemDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.lifecycle.lifecycleScope

@AndroidEntryPoint
class CheckItemDetailFragment : Fragment() {
    private var _binding: FragmentCheckItemDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CheckItemDetailFragmentArgs by navArgs()
    private val viewModel: CheckItemDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        with(args.checkItem) {
            binding.titleText.text = title
            binding.descriptionText.text = description
            binding.dateText.text = SimpleDateFormat(
                "dd MMMM yyyy HH:mm",
                Locale.getDefault()
            ).format(Date(timestamp))

            Glide.with(requireContext())
                .load(photoUrl)
                .into(binding.photoImage)
        }
    }

    private fun setupListeners() {
        binding.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_confirmation_title))
            .setMessage(getString(R.string.delete_confirmation_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                deleteCheckItem()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun deleteCheckItem() {
        viewModel.deleteCheckItem(args.checkItem)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deleteResult.collect { result ->
                when (result) {
                    is Result.Success -> {
                        showDeleteLoading(false)
                        showSuccessAndNavigateBack()
                    }
                    is Result.Error -> {
                        showDeleteLoading(false)
                        showDeleteError(result.exception)
                    }
                    Result.Loading -> {
                        showDeleteLoading(true)
                    }
                    null -> { /* İşlem yok */ }
                }
            }
        }
    }

    private fun showDeleteLoading(show: Boolean) {
        binding.deleteProgressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.deleteButton.isEnabled = !show
    }

    private fun showSuccessAndNavigateBack() {
        Snackbar.make(
            binding.root,
            getString(R.string.success_item_deleted),
            Snackbar.LENGTH_LONG
        ).show()
        findNavController().navigateUp()
    }

    private fun showDeleteError(error: Exception) {
        Snackbar.make(
            binding.root,
            getString(R.string.error_deleting_item, error.message),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 