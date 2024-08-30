package edu.ucne.composedemo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TicketList : Screen() //Consult
    @Serializable
    data class Ticket(val ticketId: Int) : Screen() //Registry
}