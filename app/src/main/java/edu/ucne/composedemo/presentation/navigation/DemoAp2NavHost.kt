package edu.ucne.composedemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.composedemo.presentation.Ticket.TicketScreen


@Composable
fun DemoAp2NavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Ticket(0)
    ){

        composable<Screen.Ticket>{
            TicketScreen()
        }
    }
}