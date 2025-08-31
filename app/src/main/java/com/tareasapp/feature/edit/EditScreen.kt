package com.tareasapp.feature.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EditScreen(
	state: EditState,
	onIntent: (EditIntent) -> Unit
) {
	LaunchedEffect(Unit) {
		onIntent(EditIntent.Load(state.tareaId))
	}

	Scaffold { padding ->
		Column(modifier = Modifier.padding(padding).padding(16.dp)) {
			OutlinedTextField(
				value = state.descripcion,
				onValueChange = { onIntent(EditIntent.DescripcionChanged(it)) },
				label = { Text("Descripción") },
				isError = state.descripcionError != null,
				modifier = Modifier.fillMaxWidth()
			)
			if (state.descripcionError != null) Text(state.descripcionError, color = MaterialTheme.colorScheme.error)

			Spacer(Modifier.height(12.dp))

			OutlinedTextField(
				value = state.tiempo,
				onValueChange = { onIntent(EditIntent.TiempoChanged(it)) },
				label = { Text("Tiempo") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				isError = state.tiempoError != null,
				modifier = Modifier.fillMaxWidth()
			)
			if (state.tiempoError != null) Text(state.tiempoError, color = MaterialTheme.colorScheme.error)

			Spacer(Modifier.height(16.dp))

			Row {
				Button(onClick = { onIntent(EditIntent.Save) }, enabled = !state.isSaving) { Text("Guardar") }
				Spacer(Modifier.width(8.dp))
				if (!state.isNew) {
					OutlinedButton(onClick = { onIntent(EditIntent.Delete) }, enabled = !state.isDeleting) { Text("Eliminar") }
				}
			}
		}
	}
}