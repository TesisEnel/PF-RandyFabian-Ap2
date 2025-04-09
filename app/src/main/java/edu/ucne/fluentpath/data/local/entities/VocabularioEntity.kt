package edu.ucne.fluentpath.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.fluentpath.data.remote.dto.VocabularioDto

@Entity(tableName = "vocabulario")

data class VocabularioEntity (
    @PrimaryKey
    val vocabularioId: Int,
    val nombre: String  = "",
    val descripcion: String = ""
)

data class VocabularioResponse(
    val data: List<VocabularioDto> = emptyList()
)


