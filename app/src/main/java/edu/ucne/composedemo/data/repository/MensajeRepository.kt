package edu.ucne.composedemo.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketGetMen
import edu.ucne.composedemo.data.remote.dto.TicketPostMen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MensajeRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMensajes(idTicket: Double): Flow<Resource<List<TicketGetMen>>> = flow {
        try {
            emit(Resource.Loading())
            val mensajes = remoteDataSource.getMensaje(idTicket)
            emit(Resource.Success(mensajes))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun postMensaje(ticketPostMen: TicketPostMen): Flow<Resource<TicketPostMen>> = flow {
        emit(Resource.Loading())
        try {
            val response = remoteDataSource.postMensaje(ticketPostMen)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de red: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }
}