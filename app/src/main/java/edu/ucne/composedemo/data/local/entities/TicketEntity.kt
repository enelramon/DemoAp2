package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey
    val idTicket: Double,
    val fecha: String? = "",
    val vence: String? = "",
    val idCliente: Double?,
    val empresa: String? = "",
    val solicitadoPor: String? = "",
    val asunto: String? = "",
    val prioridad: Int?,
    val idEncargado: Int?,
    val estatus: Int?,
    val especificaciones: String? = "",
    val archivo: String? = "",
    val empresaId: Int? = null,

)