package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.SuplidorGastoDao
import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class SuplidorGastoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val suplidorGastoDao: SuplidorGastoDao
) {
    private fun getSuplidoresGastosLocal() = suplidorGastoDao.getSuplidoresGastos()
    private suspend fun saveSuplidorGastosLocal(suplidorGastoEntity: SuplidorGastoEntity) = suplidorGastoDao.save(suplidorGastoEntity)

    fun getSuplidoresGastos(): Flow<Resource<List<SuplidorGastoEntity>>> = flow {

        emit(Resource.Loading())
        val suplidoresGastosLocal = getSuplidoresGastosLocal().firstOrNull()
        if(suplidoresGastosLocal != null&& suplidoresGastosLocal.isEmpty()){
            emit(Resource.Success(suplidoresGastosLocal))
        }
        try {
            emit(Resource.Loading())
            val suplidorGastoRemote = remoteDataSource.getSuplidoresGastos()
            suplidorGastoRemote.forEach{ suplidor ->
                saveSuplidorGastosLocal(suplidor.toEntity())
            }
            val suplidoresGastosLocalUpdated = getSuplidoresGastosLocal().firstOrNull()
            emit(Resource.Success(suplidoresGastosLocalUpdated?: emptyList()))
        }catch (e: HttpException){
            emit(Resource.Error("Error de internet"))
        }catch (e: Exception){
            emit(Resource.Error("Error Desconocido"))
        }
    }
}