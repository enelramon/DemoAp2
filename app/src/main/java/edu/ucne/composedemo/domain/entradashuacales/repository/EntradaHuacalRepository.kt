package edu.ucne.composedemo.domain.entradashuacales.repository

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import kotlinx.coroutines.flow.Flow

interface EntradaHuacalRepository {
    fun observeAll(): Flow<List<EntradaHuacal>>
    fun observeFiltered(
        nombreCliente: String?,
        fechaInicio: String?,
        fechaFin: String?
    ): Flow<List<EntradaHuacal>>
    suspend fun getById(id: Int): EntradaHuacal?
    suspend fun upsert(entradaHuacal: EntradaHuacal): Int
    suspend fun delete(id: Int)
}
