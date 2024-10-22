package edu.ucne.composedemo.presentation.gastos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.repository.Gastosrepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val gastosrepository: Gastosrepository
): ViewModel(){
    private val _uiState = MutableStateFlow(GastosUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getGastos()
    }

    private fun getGastos() {
        viewModelScope.launch {
            gastosrepository.getGastos().collectLatest { resultado ->
                when (resultado) {
                    is Resource.Loading -> {
                        Log.d("GastosViewModel", "Cargando datos de gastos...")
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        Log.d("GastosViewModel", "Datos cargados con éxito: ${resultado.data}")
                        _uiState.update {
                            it.copy(
                                gastos = resultado.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.e("GastosViewModel", "Error al cargar datos: ${resultado.message}")
                        _uiState.update {
                            it.copy(
                                errorMessage = resultado.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
