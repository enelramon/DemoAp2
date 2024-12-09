package edu.ucne.composedemo.presentation.meta

import edu.ucne.composedemo.data.remote.dto.TicketMetaResponseDto

data class TicketMetaUiState(
    val idTicket: Int = 0,
    val idUsuario: Int = 1,
    val asunto: String? = "",
    val empresa: String? = "",
    val estatus: Int? = 0,
    val estatusDescription: String? = "",
    val ticketMetas: List<TicketMetaResponseDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)
