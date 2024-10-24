package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class Gastosrepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getGastos(): Flow<Resource<List<GastoDto>>> = flow {

        try{
            emit(Resource.Loading())
            val gastosApi = remoteDataSource.getGastos()
            emit (Resource.Success(gastosApi))

        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de Internet:$errorMessage "))

        }
        catch (e: Exception){
            emit(Resource.Error("Error ${e.message}"))
        }
    }


}
