package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketingApi {
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @PUT("api/Sistemas")
    suspend fun updateSistema(@Body sistemaDto: SistemaDto): SistemaDto

//    AnydesLog
    @GET("api/AnydeskLog")
    suspend fun getAnyDeskLogs(): List<AnyDeskLogDto>
    @GET("api/AnydeskLog/{id}")
    suspend fun getAnyDeskLogById(@Path("id") id: Int): AnyDeskLogDto

}