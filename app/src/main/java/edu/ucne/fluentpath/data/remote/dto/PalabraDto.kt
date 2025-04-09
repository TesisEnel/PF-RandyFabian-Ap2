package edu.ucne.fluentpath.data.remote.dto

import edu.ucne.fluentpath.data.local.entities.PalabraEntity

data class PalabraDto(
    val id: Int? = null,
    val termino: String? = null,
    val definicion: String? = null,
    val vocabularioId: Int? = null,
    val imagenURL: String? = null
) {
    fun toEntity(): PalabraEntity {
        return PalabraEntity(
            id = id ?: 0,
            termino = termino ?: "",
            definicion = definicion ?: "",
            vocabularioId = vocabularioId ?: 0,
            imagenURL = imagenURL ?: ""
        )
    }
}