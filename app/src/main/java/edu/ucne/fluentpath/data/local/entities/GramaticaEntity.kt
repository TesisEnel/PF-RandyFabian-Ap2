package edu.ucne.fluentpath.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "gramatica")

data class GramaticaEntity (
    @PrimaryKey
    val gramaticaId: Int,
    val titulo: String  = "",
    val contenido: String = "",
    val videoUrl: String = ""
)




