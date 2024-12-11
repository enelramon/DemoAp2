package edu.ucne.composedemo.presentation.suplidorGastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuplidorGastosViewModel @Inject constructor(
    val repositorySuplidor: SuplidorGastoRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSuplidoresGastos()
    }

    fun onEvent(event: SuplidorGastoEvent){
        when(event){
            SuplidorGastoEvent.GetAllSuplidorGastos -> getSuplidoresGastos()
            SuplidorGastoEvent.GetAllSuplidorGastosByRecurrencia -> getSuplidoresGastosByRecurrencia()
        }
    }

    private fun getSuplidoresGastos() {
        viewModelScope.launch {
            repositorySuplidor.getSuplidoresGastos().collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            suplidoresGastos = result.data?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getSuplidoresGastosByRecurrencia() {
        viewModelScope.launch {
            repositorySuplidor.getSuplidoresGastos().collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            suplidoresGastos = result.data?.filter {it.esRecurrente }?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}