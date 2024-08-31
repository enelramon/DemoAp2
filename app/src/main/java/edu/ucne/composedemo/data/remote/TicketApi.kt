package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.TicketResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketApi {
    @GET("/api/Tickets")
    @Headers("X-API-Key: test")
    suspend fun getTickets(@Query("idEncargado") idEncargado: Int?) :List<TicketResponse>

    @GET("/api/Ticket/{id}")
    @Headers("X-API-Key: test")
    suspend fun getTicketById(@Path("id") ticketId: Int) :TicketResponse

}
