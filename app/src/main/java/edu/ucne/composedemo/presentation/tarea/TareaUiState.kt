package edu.ucne.composedemo.presentation.tarea

import edu.ucne.composedemo.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String = "",
    val tiempo: Int = 0,
    val errorMessage: String? = null,
    val tareas: List<TareaEntity> = emptyList()
)