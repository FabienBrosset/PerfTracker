package com.example.perftracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perftracker.model.MeasurementEntry
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour l'historique des mesures (MeasurementEntry).
 */
@Dao
interface MeasurementEntryDao {

    @Query("SELECT * FROM measurement_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<MeasurementEntry>>

    @Query("SELECT * FROM measurement_entries WHERE zoneId = :zoneId ORDER BY date DESC")
    fun getEntriesForZone(zoneId: String): Flow<List<MeasurementEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: MeasurementEntry)

    @Delete
    suspend fun deleteEntry(entry: MeasurementEntry)
}
