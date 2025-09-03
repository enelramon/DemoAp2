package edu.ucne.composedemo.domain.tareas.model

data class Task(
	val tareaId: Int = 0,
	val descripcion: String,
	val tiempo: Int
)