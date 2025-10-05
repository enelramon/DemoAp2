package edu.ucne.composedemo.presentation.entradashuacales.list

sealed interface EntradaHuacalListUiEvent {
    data object Load : EntradaHuacalListUiEvent
    data class Delete(val id: Int) : EntradaHuacalListUiEvent
    data object CreateNew : EntradaHuacalListUiEvent
    data class Edit(val id: Int) : EntradaHuacalListUiEvent
    data class ShowMessage(val message: String) : EntradaHuacalListUiEvent
    data class FilterByCliente(val nombreCliente: String) : EntradaHuacalListUiEvent
    data class FilterByFechaInicio(val fecha: String) : EntradaHuacalListUiEvent
    data class FilterByFechaFin(val fecha: String) : EntradaHuacalListUiEvent
    data object ClearFilters : EntradaHuacalListUiEvent
}
