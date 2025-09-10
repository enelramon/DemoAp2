package edu.ucne.composedemo.data.tareas.repository

import edu.ucne.composedemo.data.tareas.local.TaskDao
import edu.ucne.composedemo.data.tareas.mapper.toDomain
import edu.ucne.composedemo.data.tareas.mapper.toEntity
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
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
        dao.upsert(task.toEntity())
        return task.tareaId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
