package edu.ucne.fluentpath.data.repository

import android.util.Log
import edu.ucne.fluentpath.data.local.dao.PalabraDao
import edu.ucne.fluentpath.data.remote.a.PalabrasApi
import edu.ucne.fluentpath.data.remote.dto.PalabraDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PalabraRepository @Inject constructor(
    private val api: PalabrasApi,
    private val dao: PalabraDao
) {
    fun getPalabrasByVocabulario(vocabularioId: Int): Flow<List<PalabraDto>> = flow {
        try {
            Log.d("PalabraRepository", "Obteniendo palabras desde API para vocabulario $vocabularioId")
            val todasLasPalabras = api.getPalabras()
            Log.d("PalabraRepository", "Total palabras recibidas: ${todasLasPalabras.size}")

            // Filtramos localmente (en memoria) por vocabularioId
            val palabrasFiltradas = todasLasPalabras.filter { it.vocabularioId == vocabularioId }
            Log.d("PalabraRepository", "Palabras filtradas: ${palabrasFiltradas.size}")

            emit(palabrasFiltradas)
        } catch (e: Exception) {
            Log.e("PalabraRepository", "Error al obtener palabras", e)
            throw Exception("Error al cargar palabras: ${e.message}")
        }
    }

    suspend fun refreshPalabras(vocabularioId: Int): List<PalabraDto> {
        val todasLasPalabras = api.getPalabras()
        return todasLasPalabras.filter { it.vocabularioId == vocabularioId }
    }
}