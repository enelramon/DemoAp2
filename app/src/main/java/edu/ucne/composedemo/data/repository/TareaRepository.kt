package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.TareaDao
import edu.ucne.composedemo.data.local.entities.TareaEntity
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareaDao: TareaDao
) {
    suspend fun save(tareaEntity: TareaEntity) = tareaDao.saveTarea(tareaEntity)
    suspend fun find(tareaId: Int) = tareaDao.find(tareaId)
    suspend fun delete(tareaEntity: TareaEntity) = tareaDao.delete(tareaEntity)
    suspend fun getAll() = tareaDao.getAll()
}