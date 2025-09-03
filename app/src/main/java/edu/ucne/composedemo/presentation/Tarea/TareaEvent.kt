package edu.ucne.composedemo.presentation.Tarea

sealed interface TareaEvent {
    data class TareaIdChange(val tareaId: Int) : TareaEvent
    data class DescripcionChange(val descripcion: String) : TareaEvent
    data class TiempoChange(val tiempo: String) : TareaEvent
    data object Save : TareaEvent
    data object Delete : TareaEvent
    data object New : TareaEvent
    data class SelectedTarea(val tareaId: Int) : TareaEvent
}