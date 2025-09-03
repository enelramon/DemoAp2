package edu.ucne.composedemo.presentation.tarea

import edu.ucne.composedemo.data.local.entities.TareaEntity

sealed interface TareaEvent {
    data class TareaChange(val tareaId: Int?): TareaEvent
    data class DescripcionChange(val descripcion: String): TareaEvent
    data class TiempoChange(val tiempo: Int?): TareaEvent
    data object GoBackAfterSave: TareaEvent

    data class Delete(val tarea: TareaEntity): TareaEvent
    data object Save: TareaEvent
    data object LimpiarTodo: TareaEvent
}