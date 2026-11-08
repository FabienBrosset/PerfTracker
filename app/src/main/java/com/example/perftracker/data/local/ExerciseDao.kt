package com.example.perftracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.perftracker.model.Exercise
import com.example.perftracker.model.ExerciseType
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour les machines et exercices (Exercise).
 */
@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY displayOrder ASC, name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE isActive = 1 ORDER BY displayOrder ASC, name ASC")
    fun getActiveExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE exerciseType = :type AND isActive = 1 ORDER BY displayOrder ASC, name ASC")
    fun getExercisesByType(type: ExerciseType): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}
