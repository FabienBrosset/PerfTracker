package com.example.perftracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perftracker.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour les séances d'entraînement (WorkoutSession).
 */
@Dao
interface WorkoutSessionDao {

    @Query("SELECT * FROM workout_sessions ORDER BY date DESC")
    fun getAllSessions(): Flow<List<WorkoutSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WorkoutSession)

    @Delete
    suspend fun deleteSession(session: WorkoutSession)
}
