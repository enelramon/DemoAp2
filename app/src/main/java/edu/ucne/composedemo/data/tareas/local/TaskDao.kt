package edu.ucne.composedemo.data.tareas.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
	@Query("SELECT * FROM tasks ORDER BY tareaId DESC")
	fun observeAll(): Flow<List<TaskEntity>>

	@Query("SELECT * FROM tasks WHERE tareaId = :id")
	suspend fun getById(id: Int): TaskEntity?

	@Upsert
	suspend fun upsert(entity: TaskEntity)

	@Delete
	suspend fun delete(entity: TaskEntity)

	@Query("DELETE FROM tasks WHERE tareaId = :id")
	suspend fun deleteById(id: Int)
}