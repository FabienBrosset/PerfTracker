package com.example.perftracker.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Modèle 2 : Historique d'une prise de mesure pour une zone donnée.
 * Entité Room mappée vers la table "measurement_entries".
 */
@Entity(
    tableName = "measurement_entries",
    foreignKeys = [
        ForeignKey(
            entity = MeasurementZone::class,
            parentColumns = ["id"],
            childColumns = ["zoneId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["zoneId"])]
)
data class MeasurementEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val zoneId: String, // Clé étrangère vers MeasurementZone.id
    val date: Long = System.currentTimeMillis(), // Timestamp ms
    val value: Float, // ex: 36.5
    val notes: String? = null
)
