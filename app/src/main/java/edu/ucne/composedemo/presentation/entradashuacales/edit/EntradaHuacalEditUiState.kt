package edu.ucne.composedemo.presentation.entradashuacales.edit

data class EntradaHuacalEditUiState(
    val idEntrada: Int? = null,
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: String = "",
    val precio: String = "",
    val fechaError: String? = null,
    val nombreClienteError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null,
    val isNew: Boolean = true,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
