package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository

class GetTaskUseCase(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(id: Int): Task? = repository.getTask(id)
}