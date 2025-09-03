package edu.ucne.composedemo.presentation.Tarea

import edu.ucne.composedemo.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int = 0,
    val descripcion: String = "",
    val tiempo: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isSuccess: Boolean = false,
    val tareas: List<TareaEntity> = emptyList(),
    val validationErrors: Map<String, String> = emptyMap()
)