package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DrawerItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = if (isSelected) item.selectedIcon
                else item.unselectedIcon,
                contentDescription = item.title
            )
        },
        label = { Text(text = item.title) },
        selected = isSelected,
        onClick = onClick
    )
}