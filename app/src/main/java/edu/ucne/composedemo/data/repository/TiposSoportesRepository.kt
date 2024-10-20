package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TiposSoportesDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class TiposSoportesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getTiposSoportes(): Flow<Resource<List<TiposSoportesDto>>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.getTiposSoportes()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("Internet error: ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.message}"))
        }
    }
}