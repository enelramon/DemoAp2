package com.tareasapp.data.mapper

import com.tareasapp.data.local.TaskEntity
import com.tareasapp.domain.model.Task

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