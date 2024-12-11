package edu.ucne.composedemo.presentation.suplidorGastos

sealed interface SuplidorGastoEvent {
    data object GetAllSuplidorGastos: SuplidorGastoEvent
    data object GetAllSuplidorGastosByRecurrencia: SuplidorGastoEvent
    data object GetAllSuplidorGastosByNoRecurrente: SuplidorGastoEvent
}