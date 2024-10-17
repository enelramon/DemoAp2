package edu.ucne.composedemo.presentation.cobro

import java.util.Date

data class CobroUiState(
    val idCobro: Int? = null,
    val fecha: Date = Date(),
    val monto: Int = 0,
    val codigoCliente: Int= 0,
    val observaciones: String = " ",
    val cobros: List<Any> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    )