package edu.ucne.composedemo.data.remote.dto

data class SuplidorGastoDto(
    val idSuplidor: Int,
    val nombres: String,
    val direccion: String,
    val telefono: String,
    val celular: String,
    val fax: String,
    val rnc: String,
    val tipoNCF: Int,
    val condicion: Int,
    val email: String
)
