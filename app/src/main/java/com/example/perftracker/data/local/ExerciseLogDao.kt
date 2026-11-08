package com.example.perftracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perftracker.model.ExerciseLog
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour les performances d'exercices (ExerciseLog).
 */
@Dao
interface ExerciseLogDao {

    @Query("SELECT * FROM exercise_logs")
    fun getAllLogs(): Flow<List<ExerciseLog>>

    @Query("SELECT * FROM exercise_logs WHERE sessionId = :sessionId")
    fun getLogsForSession(sessionId: String): Flow<List<ExerciseLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ExerciseLog)

    @Delete
    suspend fun deleteLog(log: ExerciseLog)
}
