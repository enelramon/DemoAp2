package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.DepositoDao
import edu.ucne.composedemo.data.local.entities.DepositoEntity
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.DepositoDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DepositoRepository @Inject constructor(
    private val depositoDao: DepositoDao,
    private val remoteDataSource: RemoteDataSource,
) {
    fun getEntidades(): Flow<Resource<List<DepositoEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val depositoRemoto = remoteDataSource.getDepositos()

            val listaDepositos = depositoRemoto.map { dto ->
                DepositoEntity(
                    depositoId = dto.idDeposito,
                    fecha = dto.fecha,
                    idCuenta = dto.idCuenta,
                    concepto = dto.concepto,
                    monto = dto.monto
                )
            }
            depositoDao.save(listaDepositos)
            emit(Resource.Success(listaDepositos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexión $errorMessage"))
        } catch (e: Exception) {
            val depositosLocales = depositoDao.getAll().first()
            if (depositosLocales.isNotEmpty())
                emit(Resource.Success(depositosLocales))
            else
                emit(Resource.Error("Error de conexión: ${e.message}"))
        }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) = remoteDataSource.updateDeposito(id, depositoDto)
    suspend fun find(id: Int) = remoteDataSource.getDeposito(id)
    suspend fun save(depositoDto: DepositoDto) = remoteDataSource.saveDeposito(depositoDto)
    suspend fun delete(id: Int) = remoteDataSource.deleteDeposito(id)
}