package com.example.perftracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.perftracker.model.Exercise
import com.example.perftracker.model.ExerciseLog
import com.example.perftracker.model.ExerciseType
import com.example.perftracker.model.MeasurementEntry
import com.example.perftracker.model.MeasurementZone
import com.example.perftracker.model.WorkoutSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Base de données Room (Équivalent du gestionnaire de base de données SQLite en Django).
 */
@Database(
    entities = [
        MeasurementZone::class,
        MeasurementEntry::class,
        WorkoutSession::class,
        ExerciseLog::class,
        Exercise::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun measurementZoneDao(): MeasurementZoneDao
    abstract fun measurementEntryDao(): MeasurementEntryDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "perf_tracker_database"
                )
                .addCallback(DatabaseCallback())
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Popule la BDD avec des données initiales lors de la première création.
         */
        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val defaultZones = listOf(
                            MeasurementZone(
                                id= "zone_001",
                                name= "Cou",
                                category= "Haut du corps",
                                description= "Placer le ruban horizontalement juste en dessous de la pomme d'adam, sans serrer excessivement.",
                                unit= "cm",
                                displayOrder= 1,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_002",
                                name= "Épaules",
                                category= "Haut du corps",
                                description= "Faire le tour complet du corps en passant par le sommet des deltoïdes (partie la plus large des épaules). Idéalement fait avec de l'aide.",
                                unit= "cm",
                                displayOrder= 2,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_003",
                                name= "Poitrine",
                                category= "Haut du corps",
                                description= "Passer le ruban sous les aisselles et au niveau des mamelons. Mesurer en fin d'expiration normale, sans inspirer à fond.",
                                unit= "cm",
                                displayOrder= 3,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_004",
                                name= "Biceps (Droit)",
                                category= "Haut du corps",
                                description= "Bras fléchi à 90 degrés et contracté au maximum, mesurer au point le plus large de la bosse.",
                                unit= "cm",
                                displayOrder= 4,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_005",
                                name= "Biceps (Gauche)",
                                category= "Haut du corps",
                                description= "Bras fléchi à 90 degrés et contracté au maximum, mesurer au point le plus large de la bosse.",
                                unit= "cm",
                                displayOrder= 5,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_006",
                                name= "Avant-bras",
                                category= "Haut du corps",
                                description= "Bras tendu et relâché, mesurer au point le plus large juste en dessous du coude.",
                                unit= "cm",
                                displayOrder= 6,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_007",
                                name= "Taille",
                                category= "Tronc",
                                description= "Mesurer exactement au niveau du nombril en fin d'expiration, sans rentrer le ventre (repère fixe pour éviter de tricher).",
                                unit= "cm",
                                displayOrder= 7,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_008",
                                name= "Hanches / Fessiers",
                                category= "Bas du corps",
                                description= "Debout, pieds serrés, mesurer horizontalement au niveau le plus large des fesses.",
                                unit= "cm",
                                displayOrder= 8,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_009",
                                name= "Cuisse (Droite)",
                                category= "Bas du corps",
                                description= "À mi-chemin entre le pli de l'aine et le haut de la rotule, ou au point le plus haut juste sous le fessier. Rester constant.",
                                unit= "cm",
                                displayOrder= 9,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_010",
                                name= "Cuisse (Gauche)",
                                category= "Bas du corps",
                                description= "À mi-chemin entre le pli de l'aine et le haut de la rotule, ou au point le plus haut juste sous le fessier. Rester constant.",
                                unit= "cm",
                                displayOrder= 10,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_011",
                                name= "Mollet (Droit)",
                                category= "Bas du corps",
                                description= "Debout, poids du corps réparti sur les deux pieds, mesurer au point le plus large du mollet.",
                                unit= "cm",
                                displayOrder= 11,
                                isActive= true
                            ),
                            MeasurementZone(
                                id= "zone_012",
                                name= "Mollet (Gauche)",
                                category= "Bas du corps",
                                description= "Debout, poids du corps réparti sur les deux pieds, mesurer au point le plus large du mollet.",
                                unit= "cm",
                                displayOrder= 12,
                                isActive= true
                            )
                        )
                        database.measurementZoneDao().insertZones(defaultZones)

                        val defaultExercises = listOf(
                            Exercise(
                                name = "Développé couché à la barre",
                                category = "Haut du corps",
                                equipment = "Barre olympique",
                                targetMuscleGroup = "Pectoraux",
                                exerciseType = ExerciseType.MUSCULATION,
                                displayOrder = 1
                            ),
                            Exercise(
                                name = "Presse à cuisses",
                                category = "Bas du corps",
                                maxMachineWeightKg = 200f,
                                weightIncrementKg = 10f,
                                description = "Réglage du dossier au cran 3",
                                equipment = "Machine guidée",
                                targetMuscleGroup = "Quadriceps",
                                exerciseType = ExerciseType.MUSCULATION,
                                displayOrder = 2
                            ),
                            Exercise(
                                name = "Tirage vertical poulie haute",
                                category = "Haut du corps",
                                maxMachineWeightKg = 100f,
                                weightIncrementKg = 5f,
                                description = "Réglage des boudins au niveau des cuisses : cran 2",
                                equipment = "Poulie",
                                targetMuscleGroup = "Dos",
                                exerciseType = ExerciseType.MUSCULATION,
                                displayOrder = 3
                            ),
                            Exercise(
                                name = "Tapis de course",
                                category = "Cardio",
                                description = "Course continue ou séances d'intervalles",
                                equipment = "Machine cardio",
                                targetMuscleGroup = "Cardio / Jambes",
                                exerciseType = ExerciseType.CARDIO,
                                displayOrder = 4
                            )
                        )
                        database.exerciseDao().insertExercises(defaultExercises)
                    }
                }
            }
        }
    }
}
