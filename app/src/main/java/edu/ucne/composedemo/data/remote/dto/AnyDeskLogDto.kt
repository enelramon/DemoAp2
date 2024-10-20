package edu.ucne.composedemo.data.remote.dto

data class AnyDeskLogDto(
    val idCliente: Int?,
    val cliente: String,
    val cantidad: Double,
    val minutos: Int
)