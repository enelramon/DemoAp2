package edu.ucne.composedemo.presentation.ticket

sealed interface TicketEvent{
    data class TicketChange(val ticketId: Int): TicketEvent
    data class ClienteChange(val cliente:String): TicketEvent
    data class AsuntoChange(val asunto: String): TicketEvent
    data object Save: TicketEvent
    data object Delete: TicketEvent
    data object New: TicketEvent

}