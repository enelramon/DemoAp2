package edu.ucne.composedemo.presentation.Ticket

import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.TicketDto

data class TicketUiState(
    val idTicket: Double? = null,
    val fecha: String? = "",
    val vence: String? = "",
    val idCliente: Double? = null,
    val empresa: String? = "",
    val solicitadoPor: String? = "",
    val asunto: String? = "",
    val prioridad: Int? = null,
    val idEncargado: Int? = null,
    val minutosInvertidos: Int? = null,
    val estatus: Int? = null,
    val especificaciones: String? = "",
    val archivo: String? = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorCargar: String? = "",
    val errorMessage: String? = "",
    val nombreCliente: String = "",
    val tickets: List<TicketDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val sistema: List<SistemaDto> = emptyList(),

    )