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
    suspend fun saveTarea(tarea: TareaEntity)

    @Query(
        """
            select 
            * from
            Tareas where tareaId =:id 
        """
    )
    suspend fun find(id: Int): TareaEntity

    @Delete
    suspend fun delete (tarea: TareaEntity)

    @Query(
        """
            select
            * from
            Tareas
        """
    )
    fun getAll(): Flow<List<TareaEntity>>
}