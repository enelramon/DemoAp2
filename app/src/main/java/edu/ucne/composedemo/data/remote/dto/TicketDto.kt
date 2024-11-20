package edu.ucne.composedemo.data.remote.dto

data class TicketDto(
    val idTicket: Double?,
    val fecha: String?,
    val vence: String?,
    val idCliente: Double?,
    val empresa: String?,
    val solicitadoPor: String?,
    val asunto: String?,
    val prioridad: Int?,
    val idEncargado: Int?,
    val estatus: Int?,
    val especificaciones: String?,
    val archivo: String?
)
