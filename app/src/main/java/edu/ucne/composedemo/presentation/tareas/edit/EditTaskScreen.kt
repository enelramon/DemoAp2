package edu.ucne.composedemo.presentation.tareas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.jvm.Throws

@Composable
fun EditTaskScreen(
    state: EditTaskUiState,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditTaskBody(state, viewModel::onEvent)
}

@Composable
private fun EditTaskBody(
    state: EditTaskUiState,
    onEvent: (EditTaskUiEvent) -> Unit
) {

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { onEvent(EditTaskUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.descripcionError != null) {
                Text(
                    state.descripcionError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.tiempo,
                onValueChange = { onEvent(EditTaskUiEvent.TiempoChanged(it)) },
                label = { Text("Tiempo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.tiempoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.tiempoError != null) Text(
                state.tiempoError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))

            Row {
                Button(
                    onClick = { onEvent(EditTaskUiEvent.Save) },
                    enabled = !state.isSaving
                ) { Text("Guardar") }
                Spacer(Modifier.width(8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditTaskUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditTaskBodyPreview() {
    val state = EditTaskUiState()
    MaterialTheme {
        EditTaskBody(state = state) {}
    }
}