package edu.ucne.composedemo.presentation.Ticket

import edu.ucne.composedemo.data.local.entities.TicketEntity

data class TicketUiState(
    val ticketId: Int? = null,
    val cliente: String? = "",
    val asunto: String? = null,
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)