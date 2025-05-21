package edu.ucne.composedemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composedemo.presentation.ticket.TicketListScreen
import edu.ucne.composedemo.presentation.ticket.TicketScreen


@Composable
fun DemoAp2NavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TicketList
    ) {
        composable<Screen.TicketList> {
            TicketListScreen(
                goToTicket = {
                    navHostController.navigate(Screen.Ticket(it))
                },
                createTicket = {
                    navHostController.navigate(Screen.Ticket(0))
                }
            )
        }

        composable<Screen.Ticket> {
            val args = it.toRoute<Screen.Ticket>()
            TicketScreen(
                ticketId = args.ticketId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}