package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository

class UpsertTaskUseCase(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(task: Task): Result<Int> {
		val descriptionResult = validateDescripcion(task.descripcion)
		if (!descriptionResult.isValid) {
			return Result.failure(IllegalArgumentException(descriptionResult.error))
		}
		val tiempoResult = validateTiempo(task.tiempo.toString())
		if (!tiempoResult.isValid) {
			return Result.failure(IllegalArgumentException(tiempoResult.error))
		}
		return runCatching { repository.upsert(task) }
	}
}
