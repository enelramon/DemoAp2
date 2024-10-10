package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface TicketingApi {
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @PUT("api/Sistemas")
    suspend fun updateSistemas(@Body sistemaDto: SistemaDto): SistemaDto

}