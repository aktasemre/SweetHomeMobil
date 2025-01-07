package com.sweethome.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweethome.models.CheckItem
import com.sweethome.repository.CheckItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val repository: CheckItemRepository
) : ViewModel() {

    val checkItems = repository.getAllCheckItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, Result.Loading)

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun clearError() {
        _error.value = null
    }

    fun saveCheckItem(checkItem: CheckItem) {
        viewModelScope.launch {
            repository.saveCheckItem(checkItem)
        }
    }

    fun refreshItems() {
        // Room zaten Flow kullanıyor ve otomatik güncelleniyor,
        // ama manuel refresh gerekirse burada implement edilebilir
    }
} 