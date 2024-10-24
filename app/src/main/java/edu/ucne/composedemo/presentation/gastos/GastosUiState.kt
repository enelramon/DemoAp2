package edu.ucne.composedemo.presentation.gastos

import edu.ucne.composedemo.data.remote.dto.GastoDto

data class GastosUiState(
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val gastos: List<GastoDto> = emptyList()
)