package edu.ucne.composedemo.data.local.repository

import edu.ucne.composedemo.data.local.dao.EntradaHuacalDao
import edu.ucne.composedemo.data.local.mapper.toDomain
import edu.ucne.composedemo.data.local.mapper.toEntity
import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EntradaHuacalRepositoryImpl(
    private val dao: EntradaHuacalDao
) : EntradaHuacalRepository {
    override fun observeAll(): Flow<List<EntradaHuacal>> = 
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeFiltered(
        nombreCliente: String?,
        fechaInicio: String?,
        fechaFin: String?
    ): Flow<List<EntradaHuacal>> = 
        dao.observeFiltered(nombreCliente, fechaInicio, fechaFin)
            .map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: Int): EntradaHuacal? = 
        dao.getById(id)?.toDomain()

    override suspend fun upsert(entradaHuacal: EntradaHuacal): Int {
        dao.upsert(entradaHuacal.toEntity())
        return entradaHuacal.idEntrada
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
