package edu.ucne.composedemo.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val selectedItem = remember { mutableStateOf("Sistemas") }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sistema de Tickets",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    item {
                        DrawerItem(
                            "Sistemas", Icons.Filled.Info, selectedItem.value == "Sistemas",
                        ) {
                            navHostController.navigate(Screen.SistemaList)
                            selectedItem.value = "Sistemas"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Tickets", Icons.Filled.Info, selectedItem.value == "Tickets",
                        ) {
                            navHostController.navigate(Screen.TicketList)
                            selectedItem.value = "Tickets"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Clientes", Icons.Filled.Info, selectedItem.value == "Clientes",
                        ) {
                            navHostController.navigate(Screen.ClienteList)
                            selectedItem.value = "Clientes"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Equipos AnyDesks",
                            Icons.Filled.Info,
                            selectedItem.value == "Equipos AnyDesks",
                        ) {
                            navHostController.navigate(Screen.EquiposAnyDeskList)
                            selectedItem.value = "Equipos AnyDesks"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "AnyDeskLogs", Icons.Filled.Info, selectedItem.value == "AnyDeskLogs",
                        ) {
                            navHostController.navigate(Screen.AnyDeskLogList)
                            selectedItem.value = "AnyDeskLogs"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Gastos", Icons.Filled.Info, selectedItem.value == "Gastos",
                        ) {
                            navHostController.navigate(Screen.GastosList)
                            selectedItem.value = "Gastos"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Cobros", Icons.Filled.Info, selectedItem.value == "Cobros",
                        ) {
                            navHostController.navigate(Screen.CobroList)
                            selectedItem.value = "Cobros"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Tipos Soportes",
                            Icons.Filled.Info,
                            selectedItem.value == "Tipos Soportes",
                        ) {
                            navHostController.navigate(Screen.TiposSoportesList)
                            selectedItem.value = "Tipos Soportes"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Suplidores Gastos",
                            Icons.Filled.Info,
                            selectedItem.value == "Suplidores Gastos",
                        ) {
                            navHostController.navigate(Screen.SuplidoresGastosList)
                            selectedItem.value = "Suplidores Gastos"
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            "Cuentas x Cobrar",
                            Icons.Filled.Info,
                            selectedItem.value == "Cuentas x Cobrar"
                        ) {
                            navHostController.navigate(Screen.CxcList)
                            selectedItem.value = "Cuentas x Cobrar"
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ){
        content()
    }
}