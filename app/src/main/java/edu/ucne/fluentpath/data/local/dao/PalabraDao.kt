package edu.ucne.fluentpath.data.local.dao

import androidx.room.*
import edu.ucne.fluentpath.data.local.entities.PalabraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PalabraDao {
    @Upsert
    suspend fun save(entity: PalabraEntity)

    @Query("SELECT * FROM palabra WHERE vocabularioId = :vocabularioId")
    fun getByVocabulario(vocabularioId: Int): Flow<List<PalabraEntity>>

    @Query("SELECT COUNT(*) FROM palabra WHERE vocabularioId = :vocabularioId")
    suspend fun countByVocabulario(vocabularioId: Int): Int

    @Query("SELECT COUNT(*) FROM palabra")
    suspend fun countAll(): Int
}