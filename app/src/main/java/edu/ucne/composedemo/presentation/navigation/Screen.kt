package edu.ucne.composedemo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TicketList : Screen()
    @Serializable
    data class Ticket(val ticketId: Int) : Screen()
    @Serializable
    data object SistemaList : Screen()
    @Serializable
    data object ClienteList : Screen()
    @Serializable
    data object EquiposAnyDeskList : Screen()
    @Serializable
    data object AnyDeskLogList : Screen()
    @Serializable
    data object GastosList : Screen()

    @Serializable
    data object CobroList : Screen()
    @Serializable
    data object TiposSoportesList : Screen()
    @Serializable
    data object SuplidoresGastosList : Screen()
}