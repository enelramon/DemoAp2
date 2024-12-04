package edu.ucne.composedemo.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TicketMetaRequestDto(
    val idTicket: Int,
    val idUsuario: Int
)
