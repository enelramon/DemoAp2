package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.TareaDao
import edu.ucne.composedemo.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareaDao: TareaDao
) {
    suspend fun save(tarea: TareaEntity) = tareaDao.save(tarea)

    suspend fun find(id: Int): TareaEntity? = tareaDao.find(id)

    suspend fun delete(tarea: TareaEntity) = tareaDao.delete(tarea)

    fun getTareas(): Flow<List<TareaEntity>> = tareaDao.getAll()
}