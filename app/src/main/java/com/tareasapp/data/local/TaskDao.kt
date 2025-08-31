package com.tareasapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
	@Query("SELECT * FROM tasks ORDER BY tareaId DESC")
	fun observeAll(): Flow<List<TaskEntity>>

	@Query("SELECT * FROM tasks WHERE tareaId = :id")
	suspend fun getById(id: Int): TaskEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(entity: TaskEntity): Long

	@Update
	suspend fun update(entity: TaskEntity)

	@Delete
	suspend fun delete(entity: TaskEntity)

	@Query("DELETE FROM tasks WHERE tareaId = :id")
	suspend fun deleteById(id: Int)
}