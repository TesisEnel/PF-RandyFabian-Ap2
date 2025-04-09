package edu.ucne.fluentpath.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profesores")
data class ProfesorEntity(

    @PrimaryKey
    val profesorId: Int? = null,
    val nombre:String = "",
    val especialidad:String = "",
    val imagenURL: String = "",
)


