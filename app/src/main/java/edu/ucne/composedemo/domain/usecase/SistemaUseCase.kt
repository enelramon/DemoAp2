package edu.ucne.composedemo.domain.usecase

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.repository.SistemaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SistemaUseCase @Inject constructor(
    private val sistemaRepository: SistemaRepository
) {
    fun getSistemas(): Flow<Resource<List<SistemaDto>>> {
        return sistemaRepository.getSistemas()
    }

    suspend fun updateSistema(sistemaDto: SistemaDto) {
        sistemaRepository.update(sistemaDto)
    }

    fun validateCampos(nombre: String?): Boolean {
        return !nombre.isNullOrBlank()
    }
}