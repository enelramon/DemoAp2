package edu.ucne.composedemo.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.graphics.vector.ImageVector
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
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf("Sistemas") }

    val items = listOf(
        NavigationItem("Sistemas", Icons.Filled.Info, Icons.Outlined.Info, Screen.SistemaList),
        NavigationItem("Tickets", Icons.Filled.Info, Icons.Outlined.Info, Screen.TicketList),
        NavigationItem("Clientes", Icons.Filled.Info, Icons.Outlined.Info, Screen.ClienteList),
        NavigationItem("Equipos AnyDesks", Icons.Filled.Info, Icons.Outlined.Info, Screen.EquiposAnyDeskList),
        NavigationItem("AnyDeskLogs", Icons.Filled.Info, Icons.Outlined.Info, Screen.AnyDeskLogList),
        NavigationItem("Gastos", Icons.Filled.Info, Icons.Outlined.Info, Screen.GastosList),
        NavigationItem("Cobros", Icons.Filled.Info, Icons.Outlined.Info, Screen.CobroList),
        NavigationItem("Tipos Soportes", Icons.Filled.Info, Icons.Outlined.Info, Screen.TiposSoportesList),
        NavigationItem("SuplidoresGastos", Icons.Filled.Info, Icons.Outlined.Info, Screen.SuplidoresGastosList),
        NavigationItem("Cuentas x Cobrar", Icons.Filled.Info, Icons.Outlined.Info, Screen.CxcList)
    )

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
                items.forEach { item ->
                    DrawerItem(
                        item = item,
                        isSelected = selectedItem.value == item.title,
                        onClick = {
                            selectedItem.value = item.title
                            scope.launch {drawerState.close() }
                            navHostController.navigate(item.screen)
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    ){
        content()
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: Screen
)