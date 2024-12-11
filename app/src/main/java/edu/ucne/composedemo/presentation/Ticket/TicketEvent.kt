package edu.ucne.composedemo.presentation.Ticket

sealed interface TicketEvent{
    data class TicketChange(val ticketId: Int?): TicketEvent
    data class ClienteChange(val cliente:Double?): TicketEvent
    data class EncargadoChange(val idEncargado:Int?): TicketEvent
    data class AsuntoChange(val asunto: String): TicketEvent
    data class MinutosInvertidosChange(val minutos: Int?) : TicketEvent
    data object Save: TicketEvent
    data object New: TicketEvent

}