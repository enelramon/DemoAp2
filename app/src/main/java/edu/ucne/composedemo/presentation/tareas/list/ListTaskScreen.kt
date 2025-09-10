package edu.ucne.composedemo.presentation.tareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import edu.ucne.composedemo.presentation.tareas.edit.EditTaskUiState

@Composable
fun TaskListScreen(
    onDrawer: () -> Unit = {},
    viewModel: ListTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListTaskBody(state, viewModel::onEvent)
}

@Composable
fun ListTaskBody(
    state: ListTaskUiState,
    onEvent: (ListTaskUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListTaskUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_add")
            ) {
                Text("+")
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
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag("task_list")
            ) {
                items(state.tasks) { task ->
                    TaskCard(
                        task = task,
                        onClick = { onEvent(ListTaskUiEvent.Edit(task.tareaId)) },
                        onDelete = { onEvent(ListTaskUiEvent.Delete(task.tareaId)) })
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClick: (Task) -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("task_card_${'$'}{task.tareaId}")
            .clickable { onClick(task) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.descripcion, style = MaterialTheme.typography.titleMedium)
                Text("Tiempo: ${task.tiempo}")
            }
            TextButton(
                onClick = { onDelete(task.tareaId) },
                modifier = Modifier.testTag("delete_button_${'$'}{task.tareaId}")
            ) { Text("Eliminar") }
        }
    }
}

@Preview
@Composable
private fun ListTaskBodyBodyPreview() {
    MaterialTheme {
        val state = ListTaskUiState()
        ListTaskBody(state) {}
    }
}