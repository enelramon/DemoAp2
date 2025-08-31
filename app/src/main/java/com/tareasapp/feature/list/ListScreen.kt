package com.tareasapp.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tareasapp.domain.model.Task

@Composable
fun ListScreen(
	state: ListState,
	onIntent: (ListIntent) -> Unit
) {
	LaunchedEffect(Unit) {
		onIntent(ListIntent.Load)
	}

	Scaffold(
		floatingActionButton = {
			FloatingActionButton(onClick = { onIntent(ListIntent.CreateNew) }) {
				Text("+")
			}
		}
	) { padding ->
		Box(modifier = Modifier.padding(padding).fillMaxSize()) {
			if (state.isLoading) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			} else {
				TaskList(tasks = state.tasks, onClick = { onIntent(ListIntent.Edit(it.tareaId)) }, onDelete = { onIntent(ListIntent.Delete(it)) })
			}
		}
	}
}

@Composable
private fun TaskList(
	tasks: List<Task>,
	onClick: (Task) -> Unit,
	onDelete: (Int) -> Unit
) {
	LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
		items(tasks) { task ->
			Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick(task) }) {
				Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
					Column(modifier = Modifier.weight(1f)) {
						Text(task.descripcion, style = MaterialTheme.typography.titleMedium)
						Text("Tiempo: ${task.tiempo}")
					}
					TextButton(onClick = { onDelete(task.tareaId) }) { Text("Eliminar") }
				}
			}
		}
	}
}