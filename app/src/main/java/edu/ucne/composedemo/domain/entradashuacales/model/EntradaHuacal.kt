package edu.ucne.composedemo.domain.entradashuacales.model

data class EntradaHuacal(
    val idEntrada: Int = 0,
    val fecha: String,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)
