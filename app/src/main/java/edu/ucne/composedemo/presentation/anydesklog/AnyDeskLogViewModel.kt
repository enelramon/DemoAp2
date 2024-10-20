package edu.ucne.composedemo.presentation.anydesklog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.repository.AnyDeskLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnyDeskLogViewModel @Inject constructor(
    private val anyDeskLogRepository: AnyDeskLogRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnyDeskLogUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val id = 1
        getAnyDeskLogs(id)
    }

    private fun getAnyDeskLogs(id: Int) {
        viewModelScope.launch {
            anyDeskLogRepository.getAnydeskLog(id).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                anyDeskLogs = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
