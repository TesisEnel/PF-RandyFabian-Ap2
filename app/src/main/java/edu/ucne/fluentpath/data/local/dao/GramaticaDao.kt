package edu.ucne.fluentpath.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.fluentpath.data.local.entities.GramaticaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GramaticaDao {
    @Upsert
    suspend fun save(entity: GramaticaEntity)

    @Upsert
    suspend fun upsertAll(entities: List<GramaticaEntity>)

    @Query("DELETE FROM gramatica")
    suspend fun deleteAll()

    @Query("SELECT * FROM gramatica")
    fun getAll(): Flow<List<GramaticaEntity>>

    @Query("SELECT * FROM gramatica WHERE gramaticaId = :id LIMIT 1")
    fun findById(id: Int): Flow<GramaticaEntity?>

    @Query("SELECT COUNT(*) FROM gramatica")
    suspend fun getCount(): Int
}