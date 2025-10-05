package edu.ucne.composedemo.presentation.entradashuacales.edit

sealed interface EntradaHuacalEditUiEvent {
    data class Load(val id: Int?) : EntradaHuacalEditUiEvent
    data class FechaChanged(val value: String) : EntradaHuacalEditUiEvent
    data class NombreClienteChanged(val value: String) : EntradaHuacalEditUiEvent
    data class CantidadChanged(val value: String) : EntradaHuacalEditUiEvent
    data class PrecioChanged(val value: String) : EntradaHuacalEditUiEvent
    data object Save : EntradaHuacalEditUiEvent
    data object Delete : EntradaHuacalEditUiEvent
}
