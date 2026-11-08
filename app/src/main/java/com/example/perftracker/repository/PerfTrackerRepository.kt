package com.example.perftracker.repository

import com.example.perftracker.data.local.ExerciseDao
import com.example.perftracker.data.local.ExerciseLogDao
import com.example.perftracker.data.local.MeasurementEntryDao
import com.example.perftracker.data.local.MeasurementZoneDao
import com.example.perftracker.data.local.WorkoutSessionDao
import com.example.perftracker.model.Exercise
import com.example.perftracker.model.ExerciseLog
import com.example.perftracker.model.ExerciseType
import com.example.perftracker.model.MeasurementEntry
import com.example.perftracker.model.MeasurementZone
import com.example.perftracker.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

/**
 * Repository (Gère l'accès aux données persistées dans Room).
 * En Django, cela correspond aux opérations d'écriture et de lecture via l'ORM (ex: Exercise.objects.all()).
 */
class PerfTrackerRepository(
    private val zoneDao: MeasurementZoneDao,
    private val entryDao: MeasurementEntryDao,
    private val sessionDao: WorkoutSessionDao,
    private val exerciseLogDao: ExerciseLogDao,
    private val exerciseDao: ExerciseDao
) {

    // Flux réactifs d'entités depuis la base SQLite via Room
    val zones: Flow<List<MeasurementZone>> = zoneDao.getAllZones()
    val entries: Flow<List<MeasurementEntry>> = entryDao.getAllEntries()
    val sessions: Flow<List<WorkoutSession>> = sessionDao.getAllSessions()
    val exerciseLogs: Flow<List<ExerciseLog>> = exerciseLogDao.getAllLogs()
    val exercises: Flow<List<Exercise>> = exerciseDao.getAllExercises()
    val activeExercises: Flow<List<Exercise>> = exerciseDao.getActiveExercises()

    // --- Actions sur les Exercices / Machines ---
    suspend fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }

    suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }

    fun getExercisesByType(type: ExerciseType): Flow<List<Exercise>> {
        return exerciseDao.getExercisesByType(type)
    }

    // --- Operations Asynchrones pour Zones ---
    suspend fun addZone(zone: MeasurementZone) {
        zoneDao.insertZone(zone)
    }

    suspend fun deleteZone(zone: MeasurementZone) {
        zoneDao.deleteZone(zone)
    }

    // --- Operations Asynchrones pour Mesures ---
    suspend fun addMeasurementEntry(entry: MeasurementEntry) {
        entryDao.insertEntry(entry)
    }

    suspend fun deleteMeasurementEntry(entry: MeasurementEntry) {
        entryDao.deleteEntry(entry)
    }

    fun getEntriesForZone(zoneId: String): Flow<List<MeasurementEntry>> {
        return entryDao.getEntriesForZone(zoneId)
    }

    // --- Operations Asynchrones pour Séances ---
    suspend fun addWorkoutSession(session: WorkoutSession) {
        sessionDao.insertSession(session)
    }

    suspend fun deleteWorkoutSession(session: WorkoutSession) {
        sessionDao.deleteSession(session)
    }

    // --- Operations Asynchrones pour Logs d'Exercices ---
    suspend fun addExerciseLog(log: ExerciseLog) {
        exerciseLogDao.insertLog(log)
    }

    suspend fun deleteExerciseLog(log: ExerciseLog) {
        exerciseLogDao.deleteLog(log)
    }

    fun getLogsForSession(sessionId: String): Flow<List<ExerciseLog>> {
        return exerciseLogDao.getLogsForSession(sessionId)
    }
}
