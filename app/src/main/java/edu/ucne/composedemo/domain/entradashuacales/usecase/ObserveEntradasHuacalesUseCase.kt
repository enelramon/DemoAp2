package edu.ucne.composedemo.domain.entradashuacales.usecase

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository
import kotlinx.coroutines.flow.Flow

class ObserveEntradasHuacalesUseCase(
    private val repository: EntradaHuacalRepository
) {
    operator fun invoke(): Flow<List<EntradaHuacal>> = repository.observeAll()
    
    fun filtered(
        nombreCliente: String?,
        fechaInicio: String?,
        fechaFin: String?
    ): Flow<List<EntradaHuacal>> = 
        repository.observeFiltered(nombreCliente, fechaInicio, fechaFin)
}
