package edu.ucne.composedemo.presentation.tareas.list

sealed interface ListTaskUiEvent {
    data object Load : ListTaskUiEvent
    data class Delete(val id: Int) : ListTaskUiEvent
    data object CreateNew : ListTaskUiEvent
    data class Edit(val id: Int) : ListTaskUiEvent
    data class ShowMessage(val message: String) : ListTaskUiEvent
}