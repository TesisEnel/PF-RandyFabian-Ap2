package edu.ucne.fluentpath.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "palabra")
data class PalabraEntity(
    @PrimaryKey
    val id: Int? = null,
    val termino:String = "",
    val definicion: String = "",
    val vocabularioId: Int = 0,
    val imagenURL: String = "",
)