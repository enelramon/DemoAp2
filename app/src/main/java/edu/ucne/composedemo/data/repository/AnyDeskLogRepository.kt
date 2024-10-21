package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AnyDeskLogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAnydeskLog(id: Int): Flow<Resource<List<AnyDeskLogDto>>> = flow {
        try {
            emit(Resource.Loading())
            val anyDeskLog = remoteDataSource.getAnydeskLog(id)
            emit(Resource.Success(anyDeskLog))
            } catch (e: HttpException) {
            emit(Resource.Error( "Error de Internetr: ${e.message}"))
        }
        catch (e: Exception) {
            emit(Resource.Error("Error Desconocido: ${e.message}"))
        }
    }
}