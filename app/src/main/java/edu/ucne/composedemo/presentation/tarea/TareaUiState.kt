package edu.ucne.composedemo.presentation.tarea

import edu.ucne.composedemo.data.local.entities.TareaEntity

data class TareaUiState (
    val tareaId: Int? = null,
    val descripcion: String? = null,
    val errorDescripcion: String? = null,
    val tiempo: Int? = 0,
    val errorTiempo: String? = null,
    val tareas: List<TareaEntity> = emptyList(),
    val guardado: String? = null
)