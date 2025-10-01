package edu.ucne.composedemo.presentation.entradashuacales.list

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal

data class EntradaHuacalListUiState(
    val entradas: List<EntradaHuacal> = emptyList(),
    val isLoading: Boolean = false,
    val filterNombreCliente: String = "",
    val filterFechaInicio: String = "",
    val filterFechaFin: String = "",
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val message: String? = null,
    val totalEntradas: Int = 0,
    val totalPrecio: Double = 0.0
)
