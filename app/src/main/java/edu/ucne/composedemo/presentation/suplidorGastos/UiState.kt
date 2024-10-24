package edu.ucne.composedemo.presentation.suplidorGastos

import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto

data class UiState(
    val idSuplidor: Int? =null,
    val nombres: String? ="",
    val direccion: String? = "",
    val telefono: String? ="",
    val celular: String? ="",
    val fax: String? ="",
    val rnc: String? ="",
    val tipoNCF: Int? =null,
    val condicion: Int? =null,
    val email: String? ="",
    val suplidoresGastos: List<SuplidorGastoDto> = emptyList(),
    val isLoading: Boolean = false
)