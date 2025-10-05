package edu.ucne.composedemo.domain.entradashuacales.usecase

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository

class UpsertEntradaHuacalUseCase(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(entradaHuacal: EntradaHuacal): Result<Int> {
        return try {
            val validation = validateEntradaHuacal(entradaHuacal)
            if (!validation.isValid) {
                Result.failure(IllegalArgumentException(validation.error))
            } else {
                val id = repository.upsert(entradaHuacal)
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
