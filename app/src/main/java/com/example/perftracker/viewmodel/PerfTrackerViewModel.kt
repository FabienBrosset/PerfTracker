package com.example.perftracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.perftracker.data.local.AppDatabase
import com.example.perftracker.model.Exercise
import com.example.perftracker.model.ExerciseLog
import com.example.perftracker.model.MeasurementEntry
import com.example.perftracker.model.MeasurementZone
import com.example.perftracker.model.WorkoutSession
import com.example.perftracker.repository.PerfTrackerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Contrôleur / ViewModel : Fait le pont entre la Vue (Compose) et le Modèle (Room Repository).
 * 
 * En Django, ce rôle est joué par views.py (qui traite la requête HTTP,
 * interroge les Modèles/BDD, et renvoie le résultat au Template).
 */
class PerfTrackerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PerfTrackerRepository

    // Flux d'états observés par la vue
    val zones: StateFlow<List<MeasurementZone>>
    val entries: StateFlow<List<MeasurementEntry>>
    val sessions: StateFlow<List<WorkoutSession>>
    val exerciseLogs: StateFlow<List<ExerciseLog>>
    val exercises: StateFlow<List<Exercise>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PerfTrackerRepository(
            zoneDao = database.measurementZoneDao(),
            entryDao = database.measurementEntryDao(),
            sessionDao = database.workoutSessionDao(),
            exerciseLogDao = database.exerciseLogDao(),
            exerciseDao = database.exerciseDao()
        )

        zones = repository.zones.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        entries = repository.entries.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        sessions = repository.sessions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        exerciseLogs = repository.exerciseLogs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        exercises = repository.exercises.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    // Actions utilisateurs pour les Exercices / Machines
    fun createExercise(exercise: Exercise) {
        if (exercise.name.isBlank()) return
        viewModelScope.launch {
            repository.addExercise(exercise)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.updateExercise(exercise)
        }
    }

    // Logique métier pour les Zones
    fun createZone(name: String, category: String, description: String = "") {
        if (name.isBlank()) return
        val newZone = MeasurementZone(
            name = name,
            category = category,
            description = description,
            displayOrder = zones.value.size + 1
        )
        viewModelScope.launch {
            repository.addZone(newZone)
        }
    }

    fun addMeasurement(zoneId: String, value: Float, notes: String? = null) {
        val entry = MeasurementEntry(
            zoneId = zoneId,
            value = value,
            notes = notes
        )
        viewModelScope.launch {
            repository.addMeasurementEntry(entry)
        }
    }

    fun logWorkout(session: WorkoutSession) {
        viewModelScope.launch {
            repository.addWorkoutSession(session)
        }
    }

    fun logExercise(exerciseLog: ExerciseLog) {
        viewModelScope.launch {
            repository.addExerciseLog(exerciseLog)
        }
    }
}
