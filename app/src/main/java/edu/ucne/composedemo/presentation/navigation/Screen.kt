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
    data object CxcList : Screen()
    @Serializable
    data object CobroList : Screen()
    @Serializable
    data object TiposSoportesList : Screen()
    @Serializable
    data object SuplidoresGastosList : Screen()
    @Serializable
    data class GastoScreen(val idSuplidor: Int) : Screen()
    @Serializable
    data class GastoRecurrencia(val idSuplidor: Int) : Screen()
    @Serializable
    data class TicketMeta(val idUsuario: Int) : Screen()
}