package edu.ucne.composedemo.data.remote.dto

data class GastoDto(
    val idGasto: Int,            // Hacemos que idGasto sea no-nullable
    val fecha: String,
    val idSuplidor: Int,
    val suplidor: String,        // Agregamos el campo suplidor, que falta en el Dto original
    val ncf: String,
    val concepto: String,
    val descuento: Double,
    val itbis: Double,
    val monto: Double
)
