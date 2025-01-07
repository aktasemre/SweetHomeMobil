package com.sweethome.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweethome.models.CheckItem
import com.sweethome.repository.CheckItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CheckItemDetailViewModel @Inject constructor(
    private val repository: CheckItemRepository
) : ViewModel() {

    private val _deleteResult = MutableStateFlow<Result<Unit>?>(null)
    val deleteResult: StateFlow<Result<Unit>?> = _deleteResult.asStateFlow()

    fun deleteCheckItem(checkItem: CheckItem) {
        viewModelScope.launch {
            _deleteResult.value = Result.Loading
            _deleteResult.value = repository.deleteCheckItem(checkItem)
        }
    }

    fun clearDeleteResult() {
        _deleteResult.value = null
    }
} 