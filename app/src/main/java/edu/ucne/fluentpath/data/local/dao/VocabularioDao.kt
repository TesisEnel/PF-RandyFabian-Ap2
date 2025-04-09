
package edu.ucne.fluentpath.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.fluentpath.data.local.entities.VocabularioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularioDao {

    @Upsert
    suspend fun save(entity: VocabularioEntity)

    @Query("DELETE FROM vocabulario")
    suspend fun deleteAll()

    @Query("SELECT * FROM vocabulario")
    fun getAll(): Flow<List<VocabularioEntity>>

    @Query("SELECT * FROM vocabulario WHERE vocabularioId = :id LIMIT 1")
    suspend fun find(id: Int): VocabularioEntity?

    @Query("SELECT COUNT(*) FROM vocabulario")
    suspend fun getCount(): Int


}