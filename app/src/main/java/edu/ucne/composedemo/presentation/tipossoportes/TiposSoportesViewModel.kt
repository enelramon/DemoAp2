package edu.ucne.composedemo.presentation.tipossoportes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.repository.TiposSoportesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TiposSoportesViewModel @Inject constructor(
    private val tiposSoportesRepository: TiposSoportesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TiposSoportesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTiposSoportes()
    }

    private fun getTiposSoportes() {
        viewModelScope.launch {
            tiposSoportesRepository.getTiposSoportes().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(tiposSoportes = result.data ?: emptyList(),
                                isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(errorMessage = result.message,
                                isLoading = false)
                        }
                    }
                }
            }
        }
    }
}