package edu.ucne.composedemo.presentation.tareas.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.domain.tareas.model.Task

@Composable
fun TaskListScreen(
    onDrawer: () -> Unit = {},
    navigateToEditTask: (taskId: Int, goBack: () -> Unit) -> Unit,
    viewModel: ListTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTaskBody(
        state = state,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer,
        navigateToEditTask = navigateToEditTask
    )
}

@Composable
fun ListTaskBody(
    state: ListTaskUiState,
    onEvent: (ListTaskUiEvent) -> Unit,
    onDrawer: () -> Unit,
    navigateToEditTask: (taskId: Int, goBack: () -> Unit) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToEditTask(0) {
                        onEvent(ListTaskUiEvent.Load)
                    }
                },
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else if (state.tasks.isEmpty()) {
                Log.d("TaskListScreen", "No hay tareas registradas")
                Text(
                    text = "No hay tareas registradas",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag("task_list")
                ) {
                    items(state.tasks) { task ->
                        TaskCard(
                            task = task,
                            onClick = { navigateToEditTask(task.tareaId) { onEvent(ListTaskUiEvent.Load) } },
                            onEdit = { navigateToEditTask(task.tareaId) { onEvent(ListTaskUiEvent.Load) } },
                            onDelete = { onEvent(ListTaskUiEvent.Delete(task.tareaId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClick: (Task) -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("task_card_${task.tareaId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick(task) }
            ) {
                Text(task.descripcion, style = MaterialTheme.typography.titleMedium)
                Text("Tiempo: ${task.tiempo}")
            }
            IconButton(
                onClick = { onEdit(task.tareaId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("edit_button_${task.tareaId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { onDelete(task.tareaId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("delete_button_${task.tareaId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTaskBodyPreview() {
    val state = ListTaskUiState()
    ListTaskBody(
        state = state,
        onEvent = {},
        onDrawer = {},
        navigateToEditTask = { _, _ -> }
    )
}
