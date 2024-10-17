package edu.ucne.composedemo.presentation.cliente

sealed interface ClienteEvent {
    data object GetClientes : ClienteEvent
}