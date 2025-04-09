package edu.ucne.fluentpath.data.repository

import android.util.Log
import edu.ucne.fluentpath.data.local.dao.ProfesorDao
import edu.ucne.fluentpath.data.local.entities.ProfesorEntity
import edu.ucne.fluentpath.data.remote.a.ProfesoresApi
import edu.ucne.fluentpath.data.remote.dto.ProfesorDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfesorRepository @Inject constructor(
    private val dao: ProfesorDao,
    private val api: ProfesoresApi
) {
    fun getAllProfesores(): Flow<List<ProfesorEntity>> {
        return dao.getAll().map { list ->
            if (list.isEmpty()) {
                syncProfesores()
            }
            list
        }
    }

    suspend fun syncProfesores() {
        try {
            Log.d("ProfesorRepository", "Iniciando sincronización...")
            val remoteData = api.getProfesores()
            Log.d("ProfesorRepository", "Datos recibidos: ${remoteData.size} profesores")

            if (remoteData.isNotEmpty()) {
                dao.upsertAll(remoteData.map { it.toEntity() })
                Log.d("ProfesorRepository", "Datos guardados en DB")
            }
        } catch (e: Exception) {
            Log.e("ProfesorRepository", "Error en syncProfesores: ${e.message}")
            throw Exception("Error al sincronizar: ${e.message}")
        }
    }

    fun getProfesorById(id: Int): Flow<ProfesorEntity?> = dao.findById(id)

    private fun ProfesorDto.toEntity(): ProfesorEntity {
        require(profesorId != null) { "profesorId no puede ser nulo" }
        return ProfesorEntity(
            profesorId = profesorId,
            nombre = nombre ?: "",
            especialidad = especialidad ?: "",
            imagenURL = imagenURL ?: ""
        )
    }
}