package edu.ucne.composedemo.domain.usecase

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.data.repository.GastosRepository
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GastosUseCase @Inject constructor(
    private val gastosRepository: GastosRepository,
    private val suplidorGastoRepository: SuplidorGastoRepository
) {
    
    suspend fun saveGasto(gastoDto: GastoDto) {
        gastosRepository.save(gastoDto)
    }
    
    fun getSuplidoresGastos(): Flow<Resource<List<SuplidorGastoDto>>> {
        return suplidorGastoRepository.getSuplidoresGastos()
    }
    
    fun validateFields(fecha: String, ncf: String, concepto: String, monto: Double, esRecurrente: Boolean): Boolean {
        if (fecha.isBlank()) return false
        if (ncf.isBlank()) return false
        if (concepto.isBlank()) return false
        if (!esRecurrente && monto <= 0.0) return false
        return true
    }
}