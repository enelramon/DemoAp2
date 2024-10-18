package edu.ucne.composedemo.presentation.supplidorGastos

import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto

data class UiState(
    val idSuplidor: Int? =null,
    val nombres: String? =null,
    val direccion: String? =null,
    val telefono: String? =null,
    val celular: String? =null,
    val fax: String? =null,
    val rnc: String? =null,
    val tipoNCF: Int? =null,
    val condicion: Int? =null,
    val email: String? =null,
    val suplidoresGastos: List<SuplidorGastoEntity> = emptyList(),
    val isLoading: Boolean = false
)

fun UiState.toEntity() = SuplidorGastoDto(
    idSuplidor = idSuplidor,
    nombres = nombres,
    direccion = direccion,
    telefono = telefono,
    celular = celular,
    fax = fax,
    rnc = rnc,
    tipoNCF = tipoNCF,
    condicion = condicion,
    email = email
)