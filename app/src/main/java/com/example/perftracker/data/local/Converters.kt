package com.example.perftracker.data.local

import androidx.room.TypeConverter
import com.example.perftracker.model.ExerciseType
import com.example.perftracker.model.SessionType
import com.example.perftracker.model.WorkoutSet
import org.json.JSONArray
import org.json.JSONObject

/**
 * Convertisseurs de types Room pour sérialiser les objets complexes (ex: List<WorkoutSet>, Enum).
 */
class Converters {

    @TypeConverter
    fun fromSessionType(type: SessionType): String = type.name

    @TypeConverter
    fun toSessionType(value: String): SessionType = try {
        SessionType.valueOf(value)
    } catch (e: Exception) {
        SessionType.MUSCULATION
    }

    @TypeConverter
    fun fromExerciseType(type: ExerciseType): String = type.name

    @TypeConverter
    fun toExerciseType(value: String): ExerciseType = try {
        ExerciseType.valueOf(value)
    } catch (e: Exception) {
        ExerciseType.MUSCULATION
    }

    @TypeConverter
    fun fromWorkoutSetList(sets: List<WorkoutSet>?): String {
        if (sets == null) return "[]"
        val array = JSONArray()
        for (set in sets) {
            val obj = JSONObject()
            obj.put("setNumber", set.setNumber)
            obj.put("weightKg", set.weightKg)
            obj.put("reps", set.reps)
            obj.put("difficultyRpe", set.difficultyRpe)
            array.put(obj)
        }
        return array.toString()
    }

    @TypeConverter
    fun toWorkoutSetList(value: String?): List<WorkoutSet> {
        if (value.isNullOrBlank()) return emptyList()
        val list = mutableListOf<WorkoutSet>()
        try {
            val array = JSONArray(value)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    WorkoutSet(
                        setNumber = obj.getInt("setNumber"),
                        weightKg = obj.getDouble("weightKg").toFloat(),
                        reps = obj.getInt("reps"),
                        difficultyRpe = obj.getInt("difficultyRpe")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
