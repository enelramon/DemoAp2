package edu.ucne.composedemo.domain.usecase

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CxcDto
import edu.ucne.composedemo.data.repository.CxcRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CxcUseCase @Inject constructor(
    private val cxcRepository: CxcRepository
) {
    fun getCxc(clienteId: Int): Flow<Resource<List<CxcDto>>> {
        return cxcRepository.getCxc(clienteId)
    }
}