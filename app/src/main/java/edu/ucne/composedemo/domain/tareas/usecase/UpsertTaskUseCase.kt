package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository

class UpsertTaskUseCase(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(task: Task): Result<Int> {
		val d = validateDescripcion(task.descripcion)
		if (!d.isValid) return Result.failure(IllegalArgumentException(d.error))
		val t = validateTiempo(task.tiempo.toString())
		if (!t.isValid) return Result.failure(IllegalArgumentException(t.error))
		return runCatching { repository.upsert(task) }
	}
}