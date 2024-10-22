package edu.ucne.composedemo.data.remote.dto

data class GastoDto(
    val idGasto: Int,
    val fecha: String,
    val idSuplidor: Int,
    val suplidor: String,
    val ncf: String,
    val concepto: String,
    val descuento: Double,
    val itbis: Double,
    val monto: Double
)
