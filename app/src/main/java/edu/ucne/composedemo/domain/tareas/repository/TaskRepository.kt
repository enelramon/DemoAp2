package edu.ucne.composedemo.domain.tareas.repository

import edu.ucne.composedemo.domain.tareas.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
	fun observeTasks(): Flow<List<Task>>
	suspend fun getTask(id: Int): Task?
	suspend fun upsert(task: Task): Int
	suspend fun delete(id: Int)
}
