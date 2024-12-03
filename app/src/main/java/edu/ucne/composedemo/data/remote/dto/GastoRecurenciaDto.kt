package edu.ucne.composedemo.data.remote.dto

data class GastoRecurenciaDto(
    val id: Int = 0,
    val idSuplidor: Int = 0,
    val periodicidad: Int = 0,
    val dia: Int = 0,
    val monto: Double = 0.0,
    val ultimaRecurencia: String = ""
)
