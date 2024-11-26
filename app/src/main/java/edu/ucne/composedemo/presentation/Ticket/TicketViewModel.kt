package edu.ucne.composedemo.presentation.Ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.data.repository.ClienteRepository
import edu.ucne.composedemo.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getClientes()

    }
    private fun getClientes() {
        viewModelScope.launch {
            clienteRepository.getClientes().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(clientes = resource.data ?: emptyList())
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(errorMessage = resource.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun onEvent(event: TicketEvent){
        when (event){
            is TicketEvent.TicketChange -> event.ticketId?.let { onTicketIdChange(it) }
            is TicketEvent.ClienteChange -> event.cliente?.let { onClienteChange(it) }
            is TicketEvent.AsuntoChange -> onAsuntoChange(event.asunto)
            is TicketEvent.EncargadoChange -> event.idEncargado?.let { onEncargadoChange(it) }
            TicketEvent.Save -> save()
            TicketEvent.New -> nuevo()
        }
    }
    private fun save() {
        viewModelScope.launch {
            if (_uiState.value.idCliente ==null && _uiState.value.asunto.isNullOrBlank()){
                _uiState.update {
                    it.copy(errorMessage = "Campos vacios")
                }
            }
            else{
                ticketRepository.saveApi(_uiState.value.toEntity())
            }
        }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                idTicket = null,
                idCliente = null,
                asunto = "",
                fecha = "",
                vence = "",
                prioridad = null,
                idEncargado = null,
                estatus = null,
                especificaciones = "",
                archivo = "",

            )
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTickets().collect { tickets ->
                when (tickets) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                tickets = tickets.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error ->{
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorCargar = tickets.message)
                        }
                    }
                }
            }
        }
    }

    private fun onClienteChange(cliente: Double) {
        _uiState.update {
            it.copy(idCliente = cliente)
        }
    }

    private fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    private fun onTicketIdChange(ticketId: Double) {
        _uiState.update {
            it.copy(idTicket = ticketId)
        }
    }
    private fun onEncargadoChange(idEncargado: Int) {
        _uiState.update {
            it.copy(idEncargado = idEncargado)
        }
    }


    fun TicketUiState.toEntity() = TicketDto(
        idTicket = idTicket,
        idCliente = idCliente,
        asunto = asunto,
        fecha = fecha,
        vence = vence,
        prioridad = prioridad,
        idEncargado = idEncargado,
        estatus = estatus,
        especificaciones = especificaciones,
        archivo = archivo,
        empresa = empresa,
        solicitadoPor = solicitadoPor
    )
}




