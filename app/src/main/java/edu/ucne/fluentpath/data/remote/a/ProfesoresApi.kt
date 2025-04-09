package edu.ucne.fluentpath.data.remote.a

import edu.ucne.fluentpath.data.remote.dto.ProfesorDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfesoresApi {

    @GET("api/Profesors/{id}")
    suspend fun getProfesorById(@Path("id") id: Int): ProfesorDto

    @GET("api/Profesors")
    suspend fun getProfesores(): List<ProfesorDto>

    @POST("api/Profesors")
    suspend fun saveProfesor(@Body profesorDto: ProfesorDto): Response<ProfesorDto>

    @PUT("api/Profesors/{id}")
    suspend fun updateProfesor(
        @Path("id") id: Int,
        @Body profesorDto: ProfesorDto
    ): Response<ProfesorDto>

    @DELETE("api/Profesors/{id}")
    suspend fun deleteProfesor(@Path("id") id: Int): Response<Unit>
}