package com.example.perftracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Modèle 1 : Zone de référence pour les mensurations (ex: Biceps droit, Tour de taille).
 * Entité Room mappée vers la table "measurement_zones".
 */
@Entity(tableName = "measurement_zones")
data class MeasurementZone(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val category: String, // ex: "Haut du corps", "Bas du corps", "Tronc"
    val unit: String = "cm",
    val displayOrder: Int = 0,
    val isActive: Boolean = true
)
