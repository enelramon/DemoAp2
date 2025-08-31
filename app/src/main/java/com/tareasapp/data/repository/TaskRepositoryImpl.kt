package com.tareasapp.data.repository

import com.tareasapp.data.local.TaskDao
import com.tareasapp.data.mapper.toDomain
import com.tareasapp.data.mapper.toEntity
import com.tareasapp.domain.model.Task
import com.tareasapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
	private val dao: TaskDao
) : TaskRepository {
	override fun observeTasks(): Flow<List<Task>> = dao.observeAll().map { list ->
		list.map { it.toDomain() }
	}

	override suspend fun getTask(id: Int): Task? = dao.getById(id)?.toDomain()

	override suspend fun upsert(task: Task): Int {
		return if (task.tareaId == 0) {
			val newId = dao.insert(task.toEntity()).toInt()
			newId
		} else {
			dao.update(task.toEntity())
			task.tareaId
		}
	}

	override suspend fun delete(id: Int) {
		dao.deleteById(id)
	}
}