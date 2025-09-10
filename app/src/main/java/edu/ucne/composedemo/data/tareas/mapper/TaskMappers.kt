package edu.ucne.composedemo.data.tareas.mapper

import edu.ucne.composedemo.data.tareas.local.TaskEntity
import edu.ucne.composedemo.domain.tareas.model.Task

fun TaskEntity.toDomain(): Task = Task(
	tareaId = tareaId,
	descripcion = descripcion,
	tiempo = tiempo
)

fun Task.toEntity(): TaskEntity = TaskEntity(
	tareaId = tareaId,
	descripcion = descripcion,
	tiempo = tiempo
)