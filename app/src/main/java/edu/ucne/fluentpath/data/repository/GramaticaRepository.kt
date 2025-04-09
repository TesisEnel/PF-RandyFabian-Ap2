package edu.ucne.fluentpath.data.repository

import edu.ucne.fluentpath.data.local.dao.GramaticaDao
import edu.ucne.fluentpath.data.local.entities.GramaticaEntity
import edu.ucne.fluentpath.data.remote.a.GramaticaApi
import edu.ucne.fluentpath.data.remote.dto.GramaticaDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GramaticaRepository @Inject constructor(
    private val dao: GramaticaDao,
    private val api: GramaticaApi
) {
    fun getAllGramaticas(): Flow<List<GramaticaEntity>> = dao.getAll()

    fun getGramaticaById(id: Int): Flow<GramaticaEntity?> = dao.findById(id)

    suspend fun syncGramaticas() {
        try {
            val remoteData = api.getGramaticas()
            dao.upsertAll(remoteData.map { it.toEntity() })
        } catch (e: Exception) {
            throw Exception("Error al sincronizar: ${e.message}")
        }
    }

    private fun GramaticaDto.toEntity(): GramaticaEntity {
        require(gramaticaId != null) { "gramaticaId no puede ser nulo" }
        return GramaticaEntity(
            gramaticaId = gramaticaId,
            titulo = titulo ?: "",
            contenido = contenido ?: "",
            videoUrl = videoUrl ?: ""
        )
    }
}