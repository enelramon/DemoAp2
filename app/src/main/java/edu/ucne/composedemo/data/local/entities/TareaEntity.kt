package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tareas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    val tareaId: Int,
    val descripcion: String,
    val tiempo: Int
)