package edu.ucne.composedemo.presentation.entradashuacales.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun EntradaHuacalEditScreen(
    onDrawer: () -> Unit = {},
    goBack: () -> Unit = {},
    viewModel: EntradaHuacalEditViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        }
    }

    EntradaHuacalEditBody(
        state = state,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer,
        goBack = goBack
    )
}

@Composable
private fun EntradaHuacalEditBody(
    state: EntradaHuacalEditUiState,
    onEvent: (EntradaHuacalEditUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = if (state.isNew) "Nueva Entrada" else "Editar Entrada",
                onMenuClick = onDrawer
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { onEvent(EntradaHuacalEditUiEvent.FechaChanged(it)) },
                label = { Text("Fecha (YYYY-MM-DD)") },
                isError = state.fechaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.fechaError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.nombreCliente,
                onValueChange = { onEvent(EntradaHuacalEditUiEvent.NombreClienteChanged(it)) },
                label = { Text("Nombre del Cliente") },
                isError = state.nombreClienteError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreClienteError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.cantidad,
                onValueChange = { onEvent(EntradaHuacalEditUiEvent.CantidadChanged(it)) },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.cantidadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.cantidadError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.precio,
                onValueChange = { onEvent(EntradaHuacalEditUiEvent.PrecioChanged(it)) },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.precioError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEvent(EntradaHuacalEditUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (state.isSaving) "Guardando..." else "Guardar")
                }

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EntradaHuacalEditUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (state.isDeleting) "Eliminando..." else "Eliminar")
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = goBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}