package edu.ucne.composedemo.presentation.cobro

import edu.ucne.composedemo.data.remote.dto.CobroDto

data class CobroUiState(
    val cobros: List<CobroDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val idCobro: Int = 0,
    val monto: Double = 0.0,
    val codigoCliente: String = "",
    val observaciones: String = "",
    val fecha: Long = System.currentTimeMillis()
)