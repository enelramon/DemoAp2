package edu.ucne.composedemo.presentation.meta

sealed interface TicketMetaUiEvent {
    data class IdTicketChanged(val idTicket: Int): TicketMetaUiEvent
    data object Save: TicketMetaUiEvent
    data object Delete: TicketMetaUiEvent
}