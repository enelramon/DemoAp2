package edu.ucne.composedemo.domain.usecase

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.data.repository.CobroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CobroUseCase @Inject constructor(
    private val cobroRepository: CobroRepository
) {
    fun getCobros(): Flow<Resource<List<CobroDto>>> {
        return cobroRepository.getCobro()
    }
}