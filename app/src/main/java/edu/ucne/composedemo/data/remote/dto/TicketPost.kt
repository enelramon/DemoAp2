package edu.ucne.composedemo.data.remote.dto

data class TicketPost(
    val idTicket: Int,
    val enviadoPor: String,
    val mensaje: String,
    val idUsuario: Int

)