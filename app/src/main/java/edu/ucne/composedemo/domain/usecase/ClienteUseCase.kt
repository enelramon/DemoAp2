package edu.ucne.composedemo.domain.usecase

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.repository.ClienteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClienteUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    fun getClientes(): Flow<Resource<List<ClienteDto>>> {
        return clienteRepository.getClientes()
    }
}