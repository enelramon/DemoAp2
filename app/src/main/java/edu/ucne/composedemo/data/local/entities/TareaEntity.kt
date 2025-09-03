package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Tareas")
data class TareaEntity(
    @PrimaryKey
    val tareaId: Int?,
    val descripcion: String,
    val tiempo: Int // en minutos
)
