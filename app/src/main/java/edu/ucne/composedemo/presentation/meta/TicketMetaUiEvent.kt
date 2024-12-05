package edu.ucne.composedemo.presentation.meta

sealed interface TicketMetaUiEvent {
    data class idTicketChanged(val idTicket: Int): TicketMetaUiEvent
    data object Save: TicketMetaUiEvent
    data object Delete: TicketMetaUiEvent
}