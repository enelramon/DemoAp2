package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveTasksUseCase(
	private val repository: TaskRepository
) {
	operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}