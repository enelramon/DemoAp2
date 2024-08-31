package edu.ucne.composedemo.presentation.Ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
    }

    fun save() {
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

    fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                cliente = "",
                asunto = "",
                errorMessage = null
            )
        }
    }

    fun delete() {
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

    fun onClienteChange(cliente: String) {
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onTicketIdChange(ticketId: Int) {
        _uiState.update {
            it.copy(ticketId = ticketId)
        }
    }

}

data class UiState(
    val ticketId: Int? = null,
    val cliente: String? = "",
    val asunto: String? = null,
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)

fun UiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    cliente = cliente ?: "",
    asunto = asunto ?: ""
)