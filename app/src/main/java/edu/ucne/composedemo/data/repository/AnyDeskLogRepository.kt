package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AnyDeskLogRepository(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAllAnydeskLog(): Flow<Resource<List<AnyDeskLogDto>>> = flow {
        try {
            emit(Resource.Loading())
            val anyDeskLog = remoteDataSource.getAllAnydeskLog()
            emit(Resource.Success(anyDeskLog))
        } catch (e: HttpException) {
            emit(Resource.Error( "Error de Internetr: ${e.message}"))
        }
        catch (e: Exception) {
            emit(Resource.Error("Error Desconocido: ${e.message}"))
        }
    }
    fun getAnydeskLog(id: Int): Flow<Resource<AnyDeskLogDto>> = flow {
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