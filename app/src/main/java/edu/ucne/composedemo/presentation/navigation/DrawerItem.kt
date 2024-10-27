package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    navigateTo: (String) -> Unit
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected)
                    Color.Black else Color.Gray
            )
        },
        label = { Text(text = title) },
        selected = isSelected,
        onClick = { navigateTo(title) }
    )
}