package edu.ucne.composedemo.presentation.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ClienteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
    }

    fun onEvent(event: ClienteEvent) {
        when (event) {
            ClienteEvent.GetClientes -> getClientes()
        }
    }

    private fun getClientes () {
        viewModelScope.launch {
            clienteRepository.getClientes().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update{
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update{
                            it.copy(
                                clientes = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update{
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

fun ClienteUiState.toEntity() = ClienteDto(
    codigoCliente = codigoCliente ?: 0,
    nombres = nombres ?: "",
    empresa = empresa ?: "",
    direccion = direccion ?: "",
    telefono = telefono ?: "",
    celular = celular ?: "",
    rnc = rnc ?: "",
    tieneIguala = tieneIguala,
    tipoComprobante = tipoComprobante ?: 0
)