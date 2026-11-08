package com.example.perftracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Type d'exercice (Musculation vs Cardio).
 */
enum class ExerciseType(val label: String) {
    MUSCULATION("Musculation"),
    CARDIO("Cardio / Course")
}

/**
 * Modèle Exercice / Machine de salle de sport ou course à pied.
 * Entité Room mappée vers la table "exercises".
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String, // ex: "Haut du corps", "Bas du corps", "Cardio"
    val maxMachineWeightKg: Float? = null, // Poids max de la machine en kg
    val weightIncrementKg: Float? = null, // Gap entre chaque plaque (ex: 2.5, 5.0, 10.0)
    val description: String = "", // Instructions, réglages de siège, etc.
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val exerciseType: ExerciseType = ExerciseType.MUSCULATION,
    val equipment: String = "", // ex: "Poulie", "Machine guidée", "Haltères libres", "Barre olympique", "Poids du corps"
    val targetMuscleGroup: String = "", // ex: "Pectoraux", "Quadriceps", "Dos", "Abdos"
    val imageOrIcon: String? = null // Nom d'icône ou URL d'image
)
