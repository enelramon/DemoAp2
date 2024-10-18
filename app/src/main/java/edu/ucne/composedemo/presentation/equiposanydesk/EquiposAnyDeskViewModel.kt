package edu.ucne.composedemo.presentation.equiposanydesk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.repository.EquiposAnyDeskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquiposAnyDeskViewModel @Inject constructor(
    private val equiposAnyDeskRepository: EquiposAnyDeskRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(EquiposAnyDeskUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getEquiposAnyDesk()
    }

    private fun getEquiposAnyDesk() {
        viewModelScope.launch {
            equiposAnyDeskRepository.getEquiposAnyDesks().collect{ result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                equiposAnyDesks = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}