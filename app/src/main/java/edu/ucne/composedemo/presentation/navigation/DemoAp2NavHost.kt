package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.presentation.Ticket.TicketListScreen
import edu.ucne.composedemo.presentation.Ticket.TicketScreen
import edu.ucne.composedemo.presentation.anydesklog.AnyDeskLogListScreen
import edu.ucne.composedemo.presentation.cliente.ClienteListScreen
import edu.ucne.composedemo.presentation.cobro.CobroListScreen
import edu.ucne.composedemo.presentation.cxc.CxcListScreen
import edu.ucne.composedemo.presentation.equiposanydesk.EquipoAnyDeskListScreen
import edu.ucne.composedemo.presentation.gastorecurrencia.GastoRecurrenciaScreen
import edu.ucne.composedemo.presentation.gastos.GastosListScreen
import edu.ucne.composedemo.presentation.gastos.GastosScreen
import edu.ucne.composedemo.presentation.meta.TicketMetaScreen
import edu.ucne.composedemo.presentation.sistema.SistemaListScreen
import edu.ucne.composedemo.presentation.suplidorGastos.SuplidorGastosListScreen
import edu.ucne.composedemo.presentation.tareas.TareaListScreen
import edu.ucne.composedemo.presentation.tareas.TareaScreen
import edu.ucne.composedemo.presentation.tareas.TareaviewModel
import edu.ucne.composedemo.presentation.tipossoportes.TiposSoportesListScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.DeleteTareaScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaEditarScreen
import kotlinx.coroutines.launch

@Composable
fun DemoAp2NavHost(
    navHostController: NavHostController,
    tareaViewModel: TareaviewModel
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.TareaList
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

            composable("tarea_list") {
                val tarealist by tareaViewModel.tarealist.collectAsState()

                TareaListScreen(
                    tarealist = tarealist,
                    onCreate = { navHostController.navigate("tarea_nueva") },
                    onDelete = { tareaId -> navHostController.navigate("eliminar_tarea/$tareaId") },
                    onEditar = { tarea -> navHostController.navigate("editar_tarea/${tarea.tareaid}") }
                )
            }

            composable("tarea_nueva") {
                TareaScreen(
                    tarea = TareaEntity(0, "", 0),
                    viewModel = tareaViewModel,
                    onGuardar = {
                        navHostController.popBackStack()
                    },
                    onCancelar = { navHostController.popBackStack() }
                )
            }

            composable("editar_tarea/{tareaId}") { backStackEntry ->
                val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
                TareaEditarScreen(
                    tareaId = tareaId,
                    viewModel = tareaViewModel,
                    onGuardar = { navHostController.popBackStack() },
                    onCancelar = { navHostController.popBackStack() }
                )
            }

            composable("eliminar_tarea/{tareaId}") { backStackEntry ->
                val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
                DeleteTareaScreen(
                    viewModel = tareaViewModel,
                    tareaId = tareaId,
                    goBack = { navHostController.popBackStack() }
                )
            }


        }
    }
}
