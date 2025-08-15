package com.mahnoosh.database.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mahnoosh.database.data.model.HeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {
    @Upsert
    suspend fun upsertHeadlines(headlines: List<HeadlineEntity>)
    @Upsert
    suspend fun upsertHeadline(headline: HeadlineEntity)

    @Query("SELECT * FROM headline WHERE id = :headlineId")
    fun getHeadlineById(headlineId: String): Flow<HeadlineEntity>

    @Query("SELECT * FROM headline")
    fun getAllHeadlines(): Flow<List<HeadlineEntity>>
}