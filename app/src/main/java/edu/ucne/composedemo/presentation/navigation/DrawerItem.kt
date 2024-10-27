package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    drawerState: DrawerState,
    mutableState: MutableState<String>,
    navegateTo: () -> Unit
) {
    val scope = rememberCoroutineScope()
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (title == mutableState.value)
                    Color.Black else Color.Gray
            )
        },
        label = { Text(text = title) },
        selected = mutableState.value == title,
        onClick = {
            navegateTo()
            mutableState.value = title
            scope.launch { drawerState.close() }
        }
    )
}