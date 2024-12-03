package edu.ucne.composedemo.data.remote.dto

data class SuplidorGastoDto(
    val idSuplidor: Int = 0,
    val nombres: String = "",
    val direccion: String  = "",
    val telefono: String = "",
    val celular: String = "",
    val fax: String = "",
    val rnc: String = "",
    val tipoNCF: Int = 0,
    val condicion: Int = 0,
    val email: String = "",
    val esRecurrente: Boolean = false
)
