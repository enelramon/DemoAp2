package edu.ucne.composedemo.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Payments
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

    fun handleItemClick(destination: Screen, item: String) {
        navHostController.navigate(destination)
        selectedItem.value = item
        scope.launch { drawerState.close() }
    }
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
                            icon = Icons.Filled.Home,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_sistemas)
                        ) {
                            handleItemClick(Screen.SistemaList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_tickets),
                            icon = Icons.AutoMirrored.Filled.StickyNote2,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_tickets)
                        ) {
                            handleItemClick(Screen.TicketList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_clientes),
                            icon = Icons.Filled.AccountCircle,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_clientes)
                        ) {
                            handleItemClick(Screen.ClienteList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_equiposAnydesks),
                            icon = Icons.Filled.Laptop,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_equiposAnydesks)
                        ) {
                            handleItemClick(Screen.EquiposAnyDeskList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_anydeskLogs),
                            icon = Icons.Filled.Description,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_anydeskLogs)
                        ) {
                            handleItemClick(Screen.AnyDeskLogList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_gastos),
                            icon = Icons.Filled.Money,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_gastos)
                        ) {
                            handleItemClick(Screen.GastosList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_cobros),
                            icon = Icons.Filled.Paid,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_cobros)
                        ) {
                            handleItemClick(Screen.CobroList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_tiposSoportes),
                            icon = Icons.Filled.CoPresent,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_tiposSoportes)
                        ) {
                            handleItemClick(Screen.TiposSoportesList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_suplidoresGastos),
                            icon = Icons.Filled.AttachMoney,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_suplidoresGastos)
                        ) {
                            handleItemClick(Screen.SuplidoresGastosList, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_cxc),
                            icon = Icons.Filled.Payments,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_cxc)
                        ) {
                            handleItemClick(Screen.CxcList, it)
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