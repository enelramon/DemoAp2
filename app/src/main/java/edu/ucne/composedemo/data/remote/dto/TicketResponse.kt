package edu.ucne.composedemo.data.remote.dto

import com.squareup.moshi.Json

data class TicketResponse(
    val idTicket: Int,
    val fecha: String,
    val idCliente: Int,
    @Json(name = "solicitadoPor")
    val solicitado: String,
    val empresa: String,
    val asunto: String
)
