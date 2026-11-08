package com.example.perftracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.perftracker.model.MeasurementZone
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour la table des zones de mensuration (MeasurementZone).
 */
@Dao
interface MeasurementZoneDao {

    @Query("SELECT * FROM measurement_zones ORDER BY displayOrder ASC")
    fun getAllZones(): Flow<List<MeasurementZone>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZone(zone: MeasurementZone)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZones(zones: List<MeasurementZone>)

    @Update
    suspend fun updateZone(zone: MeasurementZone)

    @Delete
    suspend fun deleteZone(zone: MeasurementZone)
}
