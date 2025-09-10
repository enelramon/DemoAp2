package edu.ucne.composedemo.presentation.tareas.edit

data class EditTaskUiState(
	val tareaId: Int? = null,
	val descripcion: String = "",
	val tiempo: String = "",
	val descripcionError: String? = null,
	val tiempoError: String? = null,
	val isSaving: Boolean = false,
	val isDeleting: Boolean = false,
	val isNew: Boolean = true,
	val saved: Boolean = false,
	val deleted: Boolean = false
)
