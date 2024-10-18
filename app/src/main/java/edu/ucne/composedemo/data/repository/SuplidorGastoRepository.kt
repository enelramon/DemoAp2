package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class SuplidorGastoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    fun getSuplidoresGastos(): Flow<Resource<List<SuplidorGastoDto>>> = flow {

        try {
            emit(Resource.Loading())
            val suplidorGastoRemote = remoteDataSource.getSuplidoresGastos()
            emit(Resource.Success(suplidorGastoRemote))
        }catch (e: HttpException){
            emit(Resource.Error("Error de internet"))
        }catch (e: Exception){
            emit(Resource.Error("Error Desconocido"))
        }
    }
}