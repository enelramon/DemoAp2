package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composedemo.data.repository.FeatureFlag
import edu.ucne.composedemo.presentation.Ticket.TicketListScreen
import edu.ucne.composedemo.presentation.Ticket.TicketScreen
import edu.ucne.composedemo.presentation.anydesklog.AnyDeskLogListScreen
import edu.ucne.composedemo.presentation.cliente.ClienteListScreen
import edu.ucne.composedemo.presentation.cobro.CobroListScreen
import edu.ucne.composedemo.presentation.cxc.CxcListScreen
import edu.ucne.composedemo.presentation.equiposanydesk.EquipoAnyDeskListScreen
import edu.ucne.composedemo.presentation.gastorecurrencia.GastoRecurrenciaScreen
import edu.ucne.composedemo.presentation.gastos.GastosScreen
import edu.ucne.composedemo.presentation.gastos.GastosListScreen
import edu.ucne.composedemo.presentation.meta.TicketMetaScreen
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

    val isTicketEnabledState = remember { mutableStateOf(true) }
    val featureFlagViewModel: FeatureFlagViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        isTicketEnabledState.value = featureFlagViewModel.isEnabled(FeatureFlag.DrawerTicket)
    }

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController,
        showTicket = isTicketEnabledState.value
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
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    goToMeta = {
                        navHostController.navigate(Screen.TicketMeta(it))
                    }
                )
            }

            composable<Screen.Ticket> {
                TicketScreen(
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

            composable<Screen.GastosScreen> {
                val args = it.toRoute<Screen.GastosScreen>()
                GastosScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    idSuplidor = args.idSuplidor,
                    navigateToSuplidorGasto = {
                        navHostController.navigate(Screen.SuplidoresGastosList)
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
                    onAsignarRecurrencia = {
                        navHostController.navigate(Screen.GastoRecurrencia(it))
                    },
                    onAsignarGasto = {
                        navHostController.navigate(Screen.GastosScreen(it))
                    }
                )
            }

            composable<Screen.GastoRecurrencia>{
                val args = it.toRoute<Screen.GastoRecurrencia>()
                GastoRecurrenciaScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    idSuplidor = args.idSuplidor,
                    navigateToSuplidoresGasto = {
                        navHostController.navigate(Screen.SuplidoresGastosList)
                    }
                )
            }
            composable<Screen.TicketMeta> {
                TicketMetaScreen(
                    idUsuario = 1,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }
    }
}
