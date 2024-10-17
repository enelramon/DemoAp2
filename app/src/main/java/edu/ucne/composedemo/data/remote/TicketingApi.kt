package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface TicketingApi {
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @PUT("api/Sistemas")
    suspend fun updateSistema(@Body sistemaDto: SistemaDto): SistemaDto

    @GET("api/Cobros")
    suspend fun  getCobro(): List<CobroDto>

    @POST("api/Cobros")
    suspend fun  postCobro(): List<CobroDto>


}