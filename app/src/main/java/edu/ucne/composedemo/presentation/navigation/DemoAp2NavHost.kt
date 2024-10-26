package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composedemo.presentation.Ticket.TicketListScreen
import edu.ucne.composedemo.presentation.Ticket.TicketScreen
import edu.ucne.composedemo.presentation.anydesklog.AnyDeskLogListScreen
import edu.ucne.composedemo.presentation.cliente.ClienteListScreen
import edu.ucne.composedemo.presentation.cobro.CobroListScreen
import edu.ucne.composedemo.presentation.cxc.CxcListScreen
import edu.ucne.composedemo.presentation.equiposanydesk.EquipoAnyDeskListScreen
import edu.ucne.composedemo.presentation.gastos.GastosListScreen
import edu.ucne.composedemo.presentation.sistema.SistemaListScreen
import edu.ucne.composedemo.presentation.suplidorGastos.SuplidorGastosListScreen
import edu.ucne.composedemo.presentation.tipossoportes.TiposSoportesListScreen
import kotlinx.coroutines.launch

@Composable
fun DemoAp2NavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.SistemaList
        ) {
            composable<Screen.TicketList> {
                TicketListScreen(
                    goToTicket = {
                        navHostController.navigate(Screen.Ticket(it))
                    },
                    createTicket = {
                        navHostController.navigate(Screen.Ticket(0))
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.Ticket> {
                val args = it.toRoute<Screen.Ticket>()
                TicketScreen(
                    ticketId = args.ticketId,
                    goBack = {
                        navHostController.navigateUp()
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.SistemaList> {
                SistemaListScreen(
                    goToSistema = {},
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.ClienteList> {
                ClienteListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.EquiposAnyDeskList> {
                EquipoAnyDeskListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.AnyDeskLogList> {
                AnyDeskLogListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.CxcList> {
                CxcListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
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
            composable<Screen.CobroList> {
                CobroListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.TiposSoportesList> {
                TiposSoportesListScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.SuplidoresGastosList> {
                SuplidorGastosListScreen (
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onGoCreate = {}
                )
            }
        }
    }
}