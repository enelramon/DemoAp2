package com.tareasapp.domain.usecase

import com.tareasapp.domain.model.Task
import com.tareasapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveTasks(
	private val repository: TaskRepository
) {
	operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}

class GetTask(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(id: Int): Task? = repository.getTask(id)
}

class UpsertTask(
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

class DeleteTask(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(id: Int) = repository.delete(id)
}