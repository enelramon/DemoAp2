package edu.ucne.composedemo.presentation.cxc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CxcDto
import edu.ucne.composedemo.data.repository.CxcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CxcViewModel @Inject constructor(
    private val cxcRepository: CxcRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CxcUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCxc()
    }

    fun onEvent(event: CxcEvent) {
        when (event) {
            CxcEvent.GetCxc -> getCxc()
        }
    }

    private fun getCxc() {
        viewModelScope.launch {
            cxcRepository.getCxc(4).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                cxc = result.data ?: emptyList(),
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


fun CxcUiState.toEntity() = CxcDto(
    idVenta = idVenta ?: 0,
    fecha = fecha ?: "",
    monto = monto ?: 0f,
    balance = balance ?: 0f
)