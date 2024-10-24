package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CobroDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CobroRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
     fun getCobro(): Flow<Resource<List<CobroDto>>> = flow {
        try {
            emit(Resource.Loading())
            val cobros = remoteDataSource.getCobros()
            emit(Resource.Success(cobros))
        } catch (e: HttpException) {
            emit(Resource.Error("Error HTTP GENERAL ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error Desconocido ${e.message}"))
        }
    }
}