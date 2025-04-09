package edu.ucne.fluentpath.data.remote.a

import edu.ucne.fluentpath.data.remote.dto.PalabraDto
import retrofit2.Response
import retrofit2.http.*

interface PalabrasApi {
    @GET("api/Palabras")
    suspend fun getPalabras(): List<PalabraDto>

    @GET("api/Palabras/{id}")
    suspend fun getPalabraById(@Path("id") id: Int): PalabraDto

    @GET("api/Palabras/vocabulario/{vocabularioId}")
    suspend fun getPalabrasByVocabulario(@Path("vocabularioId") vocabularioId: Int): List<PalabraDto>

    @POST("api/Palabras")
    suspend fun savePalabra(@Body palabraDto: PalabraDto): Response<PalabraDto>

    @PUT("api/Palabras/{id}")
    suspend fun updatePalabra(@Path("id") id: Int, @Body palabraDto: PalabraDto): Response<PalabraDto>

    @DELETE("api/Palabras/{id}")
    suspend fun deletePalabra(@Path("id") id: Int): Response<Unit>
}