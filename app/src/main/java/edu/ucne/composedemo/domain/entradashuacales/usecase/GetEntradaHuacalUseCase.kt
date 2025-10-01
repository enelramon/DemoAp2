package edu.ucne.composedemo.domain.entradashuacales.usecase

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository

class GetEntradaHuacalUseCase(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(id: Int): EntradaHuacal? = repository.getById(id)
}
