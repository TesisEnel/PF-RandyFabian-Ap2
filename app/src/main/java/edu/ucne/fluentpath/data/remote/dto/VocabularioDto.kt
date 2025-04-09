
package edu.ucne.fluentpath.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import edu.ucne.fluentpath.data.local.entities.VocabularioEntity

data class VocabularioDto(
    val vocabularioId: Int? = null,
    val nombre: String? = null,
    val descripcion: String? = null
) {
    fun toEntity(): VocabularioEntity {
        require(vocabularioId != null) { "vocabularioId no puede ser nulo" }
        return VocabularioEntity(
            vocabularioId = vocabularioId,
            nombre = nombre ?: "",
            descripcion = descripcion ?: ""
        )
    }
}

