package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CxcDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CxcRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getCxc(idCliente : Int): Flow<Resource<List<CxcDto>>> = flow {
        try {
            emit(Resource.Loading())
            val cxc = remoteDataSource.getCxc(idCliente)
            emit(Resource.Success(cxc))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.message}"))
        }
    }

}