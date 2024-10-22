package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.composedemo.presentation.Ticket.TicketListScreen
import edu.ucne.composedemo.presentation.Ticket.TicketScreen
import edu.ucne.composedemo.presentation.gastos.GastosListScreen
import kotlinx.coroutines.launch


@Composable
fun DemoAp2NavHost(
    navHostController: NavHostController = rememberNavController()

) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavHost(
        navController = navHostController,
        startDestination = Screen.GastosList
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
        composable<Screen.GastosList> {
            GastosListScreen(
                onDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }

            )
        }


    }
}