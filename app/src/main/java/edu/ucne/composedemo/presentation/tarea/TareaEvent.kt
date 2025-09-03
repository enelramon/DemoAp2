package edu.ucne.composedemo.presentation.tarea

sealed interface TareaEvent {

    data class ChangeDescripcion(val descripcion: String) : TareaEvent
    data class ChangeTiempo(val tiempo: Int): TareaEvent

    data object New : TareaEvent
    data object Save : TareaEvent
    data object Delete : TareaEvent
}