package edu.ucne.composedemo.data.remote.dto

import java.util.Date

data class TicketGetMen(
    val fecha: Date,
    val enviadoPor: String,
    val mensaje: String

)