package com.sweethome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sweethome.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            // Örnek kullanıcı bilgileri
            userName.text = "Ahmet Yılmaz"
            userEmail.text = "ahmet@email.com"
            
            editProfileButton.setOnClickListener {
                // TODO: Profil düzenleme sayfasına yönlendir
            }
            
            ordersButton.setOnClickListener {
                // TODO: Siparişler sayfasına yönlendir
            }
            
            addressButton.setOnClickListener {
                // TODO: Adres yönetimi sayfasına yönlendir
            }
            
            logoutButton.setOnClickListener {
                // TODO: Çıkış işlemini gerçekleştir
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 