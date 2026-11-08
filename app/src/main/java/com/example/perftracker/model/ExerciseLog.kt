package com.example.perftracker.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Représente une série d'exercice de musculation.
 */
data class WorkoutSet(
    val setNumber: Int,
    val weightKg: Float,
    val reps: Int,
    val difficultyRpe: Int // RPE (Rate of Perceived Exertion: 1 à 10)
)

/**
 * Modèle B : Performance par exercice (Musculation ou Course à pied).
 * Entité Room mappée vers la table "exercise_logs".
 * Possède 2 clés étrangères : vers WorkoutSession et vers Exercise.
 */
@Entity(
    tableName = "exercise_logs",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["sessionId"]),
        Index(value = ["exerciseId"])
    ]
)
data class ExerciseLog(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val sessionId: String, // Lien vers WorkoutSession.id
    val exerciseId: String, // Lien vers Exercise.id
    // Données pour la musculation
    val sets: List<WorkoutSet> = emptyList(),
    // Données pour la course à pied / cardio
    val distanceKm: Float? = null,
    val durationMinutes: Float? = null,
    val averagePace: String? = null // ex: "5'18 /km"
)
