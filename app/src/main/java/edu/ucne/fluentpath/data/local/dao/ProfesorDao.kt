package edu.ucne.fluentpath.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.fluentpath.data.local.entities.ProfesorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfesorDao {
    @Upsert
    suspend fun upsertAll(entities: List<ProfesorEntity>)

    @Query("SELECT * FROM profesores")
    fun getAll(): Flow<List<ProfesorEntity>>

    @Query("SELECT * FROM profesores WHERE profesorId = :id LIMIT 1")
    fun findById(id: Int): Flow<ProfesorEntity?>
}