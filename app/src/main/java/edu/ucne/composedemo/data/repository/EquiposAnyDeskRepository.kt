package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.EquiposAnyDeskDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class EquiposAnyDeskRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getEquiposAnyDesks(): Flow<Resource<List<EquiposAnyDeskDto>>> = flow {
        try {
            emit(Resource.Loading())
            val equiposAnyDesks = remoteDataSource.getEquiposAnyDesks()

            emit(Resource.Success(equiposAnyDesks))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet: ${e.message()}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }
}