package com.tareasapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
	@PrimaryKey(autoGenerate = true)
	val tareaId: Int = 0,
	val descripcion: String,
	val tiempo: Int
)