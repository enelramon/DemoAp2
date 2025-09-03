package edu.ucne.composedemo.presentation.Tarea

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaScreen(
    tareaId: Int,
    viewModel: TareaViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(tareaId) {
        if (tareaId > 0) {
            viewModel.onEvent(TareaEvent.SelectedTarea(tareaId))
        } else {
            viewModel.onEvent(TareaEvent.New)
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            goBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tareaId == 0) "Nueva Tarea" else "Editar Tarea") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (tareaId > 0) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Descripción Field
            OutlinedTextField(
                label = { Text("Descripción") },
                value = uiState.descripcion,
                onValueChange = {
                    viewModel.onEvent(TareaEvent.DescripcionChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.validationErrors.containsKey("descripcion"),
                supportingText = {
                    uiState.validationErrors["descripcion"]?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            // Tiempo Field
            OutlinedTextField(
                label = { Text("Tiempo") },
                value = uiState.tiempo,
                onValueChange = {
                    viewModel.onEvent(TareaEvent.TiempoChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.validationErrors.containsKey("tiempo"),
                supportingText = {
                    uiState.validationErrors["tiempo"]?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                placeholder = { Text("Ej: 2 horas, 30 minutos") }
            )

            // Error Message
            if (uiState.errorMessage.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Save Button
            Button(
                onClick = {
                    viewModel.onEvent(TareaEvent.Save)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (tareaId == 0) "Guardar Tarea" else "Actualizar Tarea",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Delete Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Tarea") },
            text = { Text("¿Está seguro que desea eliminar esta tarea?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(TareaEvent.Delete)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}