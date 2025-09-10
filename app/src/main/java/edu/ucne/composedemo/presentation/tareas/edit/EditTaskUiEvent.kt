package edu.ucne.composedemo.presentation.tareas.edit

sealed interface EditTaskUiEvent {
	data class Load(val id: Int?) : EditTaskUiEvent
	data class DescripcionChanged(val value: String) : EditTaskUiEvent
	data class TiempoChanged(val value: String) : EditTaskUiEvent
	data object Save : EditTaskUiEvent
	data object Delete : EditTaskUiEvent
}
