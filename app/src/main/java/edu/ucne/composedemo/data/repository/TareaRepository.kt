package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.TareaDao
import edu.ucne.composedemo.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TareaRepository @Inject constructor(
    private val dao: TareaDao
) {
    suspend fun save(tarea: TareaEntity) = dao.save(tarea)
    suspend fun find(id: Int?): TareaEntity = dao.find(id)
    suspend fun delete(tarea: TareaEntity) = dao.delete(tarea)
    fun getAll(): Flow<List<TareaEntity>> = dao.getAll()
}