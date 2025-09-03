package edu.ucne.composedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Upsert()
    suspend fun save(tarea: TareaEntity)

    @Query("""
        SELECT * FROM Tareas WHERE tareaid = :id LIMIT 1""")
    suspend fun find(id: Int): TareaEntity?

    @Delete
    suspend fun delete(tareaEntity: TareaEntity)

    @Query("SELECT * FROM Tareas")
    fun getAll(): Flow<List<TareaEntity>>
}