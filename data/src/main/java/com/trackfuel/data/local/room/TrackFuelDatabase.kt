package com.trackfuel.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trackfuel.data.local.room.dao.*
import com.trackfuel.data.local.room.entity.*

@Database(
    entities = [
        UserProfileEntity::class,
        FoodEntryEntity::class,
        WorkoutEntryEntity::class,
        WaterEntryEntity::class,
        BodyWeightLogEntity::class,
        DaySummaryEntity::class,
        SupplementDefinitionEntity::class,
        SupplementLogEntity::class,
        ExerciseCatalogEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TrackFuelDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun workoutEntryDao(): WorkoutEntryDao
    abstract fun waterEntryDao(): WaterEntryDao
    abstract fun bodyWeightLogDao(): BodyWeightLogDao
    abstract fun daySummaryDao(): DaySummaryDao
    abstract fun supplementDao(): SupplementDao
    abstract fun exerciseCatalogDao(): ExerciseCatalogDao
}
