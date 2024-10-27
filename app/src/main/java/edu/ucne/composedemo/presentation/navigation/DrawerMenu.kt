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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val selectedItem = remember { mutableStateOf("Sistemas") }

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
                    item{
                        DrawerItem(
                            "Sistemas", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.SistemaList) }

                        DrawerItem(
                            "Tickets", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.TicketList) }

                        DrawerItem(
                            "Clientes", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.ClienteList) }

                        DrawerItem(
                            "Equipos AnyDesks", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.EquiposAnyDeskList) }

                        DrawerItem(
                            "AnyDeskLogs", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.AnyDeskLogList) }

                        DrawerItem(
                            "Gastos", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.GastosList) }

                        DrawerItem(
                            "Cobros", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.CobroList) }

                        DrawerItem(
                            "Tipos Soportes", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.TiposSoportesList) }

                        DrawerItem(
                            "Suplidores Gastos", Icons.Filled.Info, drawerState, selectedItem,
                        ){ navHostController.navigate(Screen.SuplidoresGastosList) }

                        DrawerItem(
                            "Cuentas x Cobrar", Icons.Filled.Info, drawerState, selectedItem
                        ){ navHostController.navigate(Screen.CxcList) }
                    }
                }
            }
        },
        drawerState = drawerState
    ){
        content()
    }
}