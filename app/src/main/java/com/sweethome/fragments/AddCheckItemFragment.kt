package com.sweethome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sweethome.databinding.FragmentAddCheckItemBinding
import com.sweethome.models.CheckItem
import com.sweethome.viewmodels.AddCheckItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddCheckItemFragment : Fragment() {
    private var _binding: FragmentAddCheckItemBinding? = null
    private val binding get() = _binding!!
    private val args: AddCheckItemFragmentArgs by navArgs()
    private val viewModel: AddCheckItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCheckItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Çekilen fotoğrafı göster
        Glide.with(this)
            .load(args.photoUri)
            .into(binding.photoPreview)

        binding.saveButton.setOnClickListener {
            saveCheckItem()
        }
    }

    private fun saveCheckItem() {
        val title = binding.titleInput.text.toString()
        val description = binding.descriptionInput.text.toString()

        if (title.isNotBlank()) {
            val checkItem = CheckItem(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                photoUrl = args.photoUri,
                timestamp = System.currentTimeMillis()
            )
            viewModel.saveCheckItem(checkItem)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 