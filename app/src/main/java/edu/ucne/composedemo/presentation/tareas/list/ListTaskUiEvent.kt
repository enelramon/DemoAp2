package edu.ucne.composedemo.presentation.tareas.list

sealed class ListTaskUiEvent {
    object Load : ListTaskUiEvent()
    object Refresh : ListTaskUiEvent()
    data class Delete(val id: Int) : ListTaskUiEvent()
    data class ShowMessage(val message: String) : ListTaskUiEvent()
    object ClearMessage : ListTaskUiEvent()
    object CreateNew : ListTaskUiEvent()
    data class Edit(val id: Int) : ListTaskUiEvent()
}