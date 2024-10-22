package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.GastoDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface TicketingApi {
    @Headers("X-API-Key:test")
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @Headers("X-API-Key:test")
    @PUT("api/Sistemas")
    suspend fun updateSistema(@Body sistemaDto: SistemaDto): SistemaDto

    @Headers("X-API-Key:test")
    @GET("api/Gastos")
    suspend fun getGastos(): List<GastoDto>



}