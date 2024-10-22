package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composedemo.presentation.navigation.DemoAp2NavHost
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAp2Theme {
                val navHost = rememberNavController()
                DemoAp2NavHost(navHost)
            }
        }
    }
}

fun NavigationItems() : List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Proovedores",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,

            ),
        NavigationItem(
            title = "Categorias",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Build,
        ),
        NavigationItem(
            title = "Productos",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Build,
        )
    )
}



