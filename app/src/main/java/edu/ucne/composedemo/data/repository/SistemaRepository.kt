package edu.ucne.composedemo.data.repository

import android.util.Log
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    fun getSistemas(): Flow<Resource<List<SistemaDto>>> = flow {
        try {
            emit(Resource.Loading())
            val sistemas = remoteDataSource.getSistemas()

            //Todo: Insertar "sistemas" a la base de datos room

            //val sistemasLocal = getSistemasLocal()
            emit(Resource.Success(sistemas))
        } catch (e: HttpException) {
            Log.e("Retrofit No connection", "Error de connection ${e.message} ", e)
            emit(Resource.Error("Error de internet ${e.message} "))
        } catch (e: Exception) {
            Log.e("Retrofit Unknown ", "Error desconocido ${e.message} ", e)
            emit(Resource.Error("Unknown error ${e.message} "))
        }
    }

    suspend fun update(sistemaDto: SistemaDto) = remoteDataSource.updateSistemas(sistemaDto)
}