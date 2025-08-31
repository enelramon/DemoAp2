package com.tareasapp.feature.edit

sealed interface EditIntent {
	data class Load(val id: Int?) : EditIntent
	data class DescripcionChanged(val value: String) : EditIntent
	data class TiempoChanged(val value: String) : EditIntent
	data object Save : EditIntent
	data object Delete : EditIntent
}

data class EditState(
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

sealed interface EditEffect {
	data class ShowMessage(val message: String) : EditEffect
}