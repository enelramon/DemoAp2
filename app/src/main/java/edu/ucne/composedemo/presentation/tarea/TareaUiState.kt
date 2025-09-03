package edu.ucne.composedemo.presentation.tarea

import edu.ucne.composedemo.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String? = "",
    val tiempo: Int? = null,
    val errorMessageDescripcion: String? = "",
    val errorMessageTiempo: String? = "",
    val guardado: Boolean = false,
    val listaTareas: List<TareaEntity> = emptyList()
)
