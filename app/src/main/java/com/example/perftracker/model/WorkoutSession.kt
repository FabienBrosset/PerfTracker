package com.example.perftracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Type de séance de sport.
 */
enum class SessionType(val label: String) {
    MUSCULATION("Musculation"),
    RUNNING("Course à pied")
}

/**
 * Modèle A : Représente une séance d'entraînement.
 * Entité Room mappée vers la table "workout_sessions".
 */
@Entity(tableName = "workout_sessions")
data class WorkoutSession(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val date: Long = System.currentTimeMillis(),
    val sessionType: SessionType = SessionType.MUSCULATION,
    val fatigueLevel: Int = 1, // Niveau de 1 à 5
    val moodOrEnergy: String = "", // Note sur l'état d'esprit / énergie
    val notes: String? = null
)
