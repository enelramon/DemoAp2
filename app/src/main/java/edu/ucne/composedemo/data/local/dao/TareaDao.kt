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

    @Query(
        """
        SELECT * 
        FROM Tareas 
        WHERE tareaId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): TareaEntity?

    @Delete
    suspend fun delete(tarea: TareaEntity)

    @Query("SELECT * FROM Tareas ORDER BY tareaId DESC")
    fun getAll(): Flow<List<TareaEntity>>
}