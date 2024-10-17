package edu.ucne.composedemo.data.remote.dto

import java.util.Date

data class AnyDeskLogDto(
    val idCliente: Int?,
    val cliente: String?,
    val cantidad: Double?,
    val minutos: Int?,
    val fecha: Date?,
    val origen: String?,
    val destino: String?,
    val desde: Date?,
    val hasta: Date?,
    val idEncargado: Int?,
)