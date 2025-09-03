package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.repository.TaskRepository

class DeleteTaskUseCase(
	private val repository: TaskRepository
) {
	suspend operator fun invoke(id: Int) = repository.delete(id)
}