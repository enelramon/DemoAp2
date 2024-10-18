package edu.ucne.composedemo.data.remote.dto

import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity

data class SuplidorGastoDto(
    val idSuplidor: Int? =null,
    val nombres: String? =null,
    val direccion: String? =null,
    val telefono: String? =null,
    val celular: String? =null,
    val fax: String? =null,
    val rnc: String? =null,
    val tipoNCF: Int? =null,
    val condicion: Int? =null,
    val email: String? =null
)

fun SuplidorGastoDto.toEntity() = SuplidorGastoEntity(
    idSuplidor = idSuplidor?:0,
    nombres = nombres?:"",
    direccion = direccion?:"",
    telefono = telefono?:"",
    celular = celular?:"",
    fax = fax?:"",
    rnc = rnc?:"",
    tipoNCF = tipoNCF?:0,
    condicion = condicion?:0,
    email = email?:""
)
