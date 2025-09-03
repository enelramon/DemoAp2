package edu.ucne.composedemo.presentation.tarea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun TareasList(
    viewModel: TareaViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onEdit: (Int?) -> Unit,
    createTarea: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TareasListBodyScreen(
        uiState,
        onDrawer,
        createTarea,
        onEdit,
        onDelete = { tarea ->
            viewModel.onEvent(TareaEvent.ChangeTareaId(tarea.tareaId))
            viewModel.onEvent(TareaEvent.Delete)
        },
        actulizarId = { id ->
            viewModel.onEvent(TareaEvent.ChangeTareaId(id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasListBodyScreen(
    uiState: TareaUiState,
    onDrawer: () -> Unit,
    createTarea: () -> Unit,
    onEdit: (Int?) -> Unit,
    onDelete: (TareaEntity) -> Unit,
    actulizarId: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Tarea",
                onMenuClick = onDrawer

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTarea
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create a new Ticket"
                )
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(uiState.tareas) { tarea ->
                    TareaRow(
                        tarea = tarea,
                        onEdit = onEdit,
                        actulizarId = { id ->
                            actulizarId(id)
                            showDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = {
                Text(
                    "¿Estás seguro de que deseas eliminar esta tarea "
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val tarea = uiState.tareas.firstOrNull { it.tareaId == uiState.tareaId }
                    tarea?.let { onDelete(it) }
                    showDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TareaRow(
    tarea: TareaEntity,
    onEdit: (Int?) -> Unit,
    actulizarId: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "ID: ${tarea.tareaId}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "Descripción: ",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Tiempo: " + tarea.tiempo.toString() + " minutos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onEdit(tarea.tareaId) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { actulizarId(tarea.tareaId) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
