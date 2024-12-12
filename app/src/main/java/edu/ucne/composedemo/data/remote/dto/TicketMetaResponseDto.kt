package edu.ucne.composedemo.data.remote.dto

data class TicketMetaResponseDto(
    val id: Int,
    val idTicket: Int?,
    val asunto: String?,
    val empresa: String?,
    val estatus: Int?,
    val estatusDescription: String?
)