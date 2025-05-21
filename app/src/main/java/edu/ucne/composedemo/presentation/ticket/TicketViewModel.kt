package edu.ucne.composedemo.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TicketViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
    }

    fun onEvent(event: TicketEvent){
        when (event){
            is TicketEvent.TicketChange -> onTicketIdChange(event.ticketId)
            is TicketEvent.ClienteChange -> onClienteChange(event.cliente)
            is TicketEvent.AsuntoChange -> onAsuntoChange(event.asunto)
            TicketEvent.Save -> save()
            TicketEvent.Delete -> delete()
            TicketEvent.New -> nuevo()
        }
    }
    private fun save() {
        viewModelScope.launch {
            if (_uiState.value.cliente.isNullOrBlank() && _uiState.value.asunto.isNullOrBlank()){
                _uiState.update {
                    it.copy(errorMessage = "Campos vacios")
                }
            }
            else{
                ticketRepository.save(_uiState.value.toEntity())
            }
        }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                cliente = "",
                asunto = "",
                errorMessage = null
            )
        }
    }

    fun selectedTicket(ticketId: Int){
        viewModelScope.launch {
            if(ticketId > 0){
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket?.ticketId,
                        cliente = ticket?.cliente,
                        asunto = ticket?.asunto
                    )
                }
            }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            ticketRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTickets().collect { tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }

    private fun onClienteChange(cliente: String) {
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }

    private fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    private fun onTicketIdChange(ticketId: Int) {
        _uiState.update {
            it.copy(ticketId = ticketId)
        }
    }
}



fun TicketUiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    cliente = cliente ?: "",
    asunto = asunto ?: ""
)

