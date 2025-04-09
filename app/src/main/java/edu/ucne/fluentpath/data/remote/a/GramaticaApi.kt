package edu.ucne.fluentpath.data.remote.a

import edu.ucne.fluentpath.data.remote.dto.GramaticaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GramaticaApi {


    @GET("api/Gramaticas/{id}")
    suspend fun getGramaticaById(@Path("id") id: Int): GramaticaDto

    @GET("api/Gramaticas")
    suspend fun getGramaticas(): List<GramaticaDto>

    @POST("api/Gramaticas")
    suspend fun saveGramatica(@Body gramaticaDto: GramaticaDto): Response<GramaticaDto>

    @PUT("api/Gramaticas/{id}")
    suspend fun updateGramatica(
        @Path("id") id: Int,
        @Body gramaticaDto: GramaticaDto
    ): Response<GramaticaDto>

    @DELETE("api/Gramaticas/{id}")
    suspend fun deleteGramatica(@Path("id") id: Int): Response<Unit>
}