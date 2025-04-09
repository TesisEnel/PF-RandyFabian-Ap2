

package edu.ucne.fluentpath.data.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import edu.ucne.fluentpath.data.local.dao.VocabularioDao
import edu.ucne.fluentpath.data.local.entities.VocabularioEntity
import edu.ucne.fluentpath.data.local.entities.VocabularioResponse
import edu.ucne.fluentpath.data.remote.a.VocabulariosApi
import edu.ucne.fluentpath.data.remote.dto.VocabularioDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabularioRepository @Inject constructor(
    private val api: VocabulariosApi,
    private val dao: VocabularioDao
) {
    fun getAllVocabularios(): Flow<List<VocabularioEntity>> = dao.getAll()

    suspend fun syncVocabularios() {
        try {
            Log.d("Repository", "Iniciando sincronización...")
            val remoteData = api.getVocabularios()
            Log.d("Repository", "Datos recibidos: ${remoteData.size} items")

            val localData = dao.getAll().first()

            if (remoteData.size != localData.size ||
                !remoteData.map { it.vocabularioId }.containsAll(localData.map { it.vocabularioId })) {

                Log.d("Repository", "Datos diferentes, actualizando...")
                dao.deleteAll()
                remoteData.forEach { dto ->
                    try {
                        val entity = dto.toEntity()
                        dao.save(entity)
                    } catch (e: Exception) {
                        Log.e("Repository", "Error guardando item: ${e.message}")
                    }
                }
            } else {
                Log.d("Repository", "Datos ya están sincronizados")
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error en syncVocabularios: ${e.message}")
            throw e
        }
    }

    suspend fun getVocabulario(id: Int): VocabularioEntity? {
        return try {
            dao.find(id) ?: run {
                val remote = api.getVocabularioById(id)
                remote.toEntity().also { dao.save(it) }
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error obteniendo vocabulario $id: ${e.message}")
            null
        }
    }
}