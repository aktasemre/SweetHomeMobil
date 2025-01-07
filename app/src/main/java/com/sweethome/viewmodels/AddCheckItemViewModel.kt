package com.sweethome.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweethome.models.CheckItem
import com.sweethome.repository.CheckItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCheckItemViewModel @Inject constructor(
    private val repository: CheckItemRepository
) : ViewModel() {

    fun saveCheckItem(checkItem: CheckItem) {
        viewModelScope.launch {
            repository.saveCheckItem(checkItem)
        }
    }
} 