package edu.ucne.fluentpath.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.fluentpath.data.local.dao.GramaticaDao
import edu.ucne.fluentpath.data.local.dao.PalabraDao
import edu.ucne.fluentpath.data.local.dao.ProfesorDao
import edu.ucne.fluentpath.data.local.dao.VocabularioDao
import edu.ucne.fluentpath.data.local.entities.GramaticaEntity
import edu.ucne.fluentpath.data.local.entities.PalabraEntity
import edu.ucne.fluentpath.data.local.entities.ProfesorEntity
import edu.ucne.fluentpath.data.local.entities.VocabularioEntity



@Database(
    entities = [VocabularioEntity::class, PalabraEntity::class, GramaticaEntity::class, ProfesorEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun palabraDao(): PalabraDao
    abstract fun vocabularioDao(): VocabularioDao
    abstract fun gramaticaDao(): GramaticaDao
    abstract fun profesorDao(): ProfesorDao
}