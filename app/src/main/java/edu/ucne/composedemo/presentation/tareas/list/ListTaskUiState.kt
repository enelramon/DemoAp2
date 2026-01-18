package edu.ucne.composedemo.presentation.tareas.list

import edu.ucne.composedemo.domain.tareas.model.Task

data class ListTaskUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)