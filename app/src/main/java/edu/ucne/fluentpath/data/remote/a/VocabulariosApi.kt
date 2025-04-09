package edu.ucne.fluentpath.data.remote.a

import edu.ucne.fluentpath.data.remote.dto.PalabraDto
import edu.ucne.fluentpath.data.remote.dto.VocabularioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VocabulariosApi {


    @GET("api/Vocabularios/{id}")
    suspend fun getVocabularioById(@Path("id") id: Int): VocabularioDto

    @GET("api/Vocabularios")
    suspend fun getVocabularios(): List<VocabularioDto>


    @POST("api/Vocabularios")
    suspend fun saveVocabulario(@Body vocabularioDto: VocabularioDto): Response<VocabularioDto>

    @PUT("api/Vocabularios/{id}")
    suspend fun updateVocabulario(@Path("id") id: Int, @Body vocabularioDto: VocabularioDto?): Response<VocabularioDto>

    @DELETE("api/Vocabularios/{id}")
    suspend fun deleteVocabulario(@Path("id") id: Int): Response<Unit>
}