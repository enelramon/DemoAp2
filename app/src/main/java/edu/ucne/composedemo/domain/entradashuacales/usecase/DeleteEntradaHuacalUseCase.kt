package edu.ucne.composedemo.domain.entradashuacales.usecase

import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository

class DeleteEntradaHuacalUseCase(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
