package com.tareasapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val colorScheme = darkColorScheme()

@Composable
fun TareasTheme(content: @Composable () -> Unit) {
	MaterialTheme(
		colorScheme = colorScheme,
		content = content
	)
}