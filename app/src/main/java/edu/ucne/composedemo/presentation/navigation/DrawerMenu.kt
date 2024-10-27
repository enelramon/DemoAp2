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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.composedemo.R
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
                            title = stringResource(R.string.drawer_sistemas),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_sistemas)
                        ) {
                            navHostController.navigate(Screen.SistemaList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_tickets),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_tickets)
                        ) {
                            navHostController.navigate(Screen.TicketList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_clientes),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_clientes)
                        ) {
                            navHostController.navigate(Screen.ClienteList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_equiposAnydesks),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_equiposAnydesks)
                        ) {
                            navHostController.navigate(Screen.EquiposAnyDeskList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_anydeskLogs),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_anydeskLogs)
                        ) {
                            navHostController.navigate(Screen.AnyDeskLogList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_gastos),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_gastos)
                        ) {
                            navHostController.navigate(Screen.GastosList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_cobros),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_cobros)
                        ) {
                            navHostController.navigate(Screen.CobroList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_tiposSoportes),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_tiposSoportes)
                        ) {
                            navHostController.navigate(Screen.TiposSoportesList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_suplidoresGastos),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_suplidoresGastos)
                        ) {
                            navHostController.navigate(Screen.SuplidoresGastosList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_cxc),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_cxc)
                        ) {
                            navHostController.navigate(Screen.CxcList)
                            selectedItem.value = it
                            scope.launch { drawerState.close() }
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
