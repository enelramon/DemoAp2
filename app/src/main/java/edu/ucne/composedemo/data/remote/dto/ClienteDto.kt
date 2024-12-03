package edu.ucne.composedemo.data.remote.dto

data class ClienteDto(
    val codigoCliente: Double,
    val nombres: String,
    val empresa: String,
    val direccion: String,
    val telefono: String,
    val celular: String,
    val rnc: String,
    val tieneIguala: Boolean,
    val tipoComprobante: Int
)