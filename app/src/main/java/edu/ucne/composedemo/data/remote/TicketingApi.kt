package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface TicketingApi {
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @PUT("api/Sistemas")
    suspend fun updateSistema(@Body sistemaDto: SistemaDto): SistemaDto

    //SuplidoresGastos
    @GET("api/SuplidoresGastos")
    @Headers("X-API-Key","test")
    suspend fun getSuplidoresGastos(): List<SuplidorGastoDto>

}