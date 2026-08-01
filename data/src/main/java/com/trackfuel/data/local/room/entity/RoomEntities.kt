package com.trackfuel.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val sex: String,
    val birthDate: String, // ISO yyyy-MM-dd
    val heightCm: Double,
    val weightKg: Double,
    val activityLevel: String,
    val goalType: String,
    val targetWeightKg: Double?,
    val targetDeltaKcal: Int,
    val targetProteinGrams: Int,
    val units: String,
    val timezoneId: String,
    val syncTimezoneWithDevice: Boolean,
    val countExerciseSeparately: Boolean,
    val formulaId: String,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "food_entry",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "localDate"]),
        Index(value = ["createdAt"])
    ]
)
data class FoodEntryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val localDate: String,
    val name: String,
    val caloriesPerUnit: Double,
    val portionQty: Double,
    val portionUnit: String,
    val caloriesTotal: Int,
    val proteinGrams: Int,
    val mealSlot: String,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "workout_entry",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "localDate"]),
        Index(value = ["createdAt"])
    ]
)
data class WorkoutEntryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val localDate: String,
    val exerciseKey: String,
    val displayName: String,
    val durationMin: Int,
    val intensity: String,
    val metUsed: Double,
    val caloriesBurned: Int,
    val caloriesSource: String,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "water_entry",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "localDate"])
    ]
)
data class WaterEntryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val localDate: String,
    val amountMl: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "body_weight_log",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "localDate"])
    ]
)
data class BodyWeightLogEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val localDate: String,
    val weightKg: Double,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "day_summary",
    primaryKeys = ["userId", "localDate"],
    indices = [
        Index(value = ["userId", "localDate"])
    ]
)
data class DaySummaryEntity(
    val userId: String,
    val localDate: String,
    val consumedKcal: Int,
    val consumedProteinGrams: Int,
    val exerciseBurnKcal: Int,
    val bmrKcal: Int,
    val tdeeKcal: Int,
    val totalBurnedKcal: Int,
    val netKcal: Int,
    val calorieTargetKcal: Int,
    val budgetRemainingKcal: Int,
    val waterMl: Int,
    val achievementCode: String,
    val underFueled: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(
    tableName = "supplement_definition",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SupplementDefinitionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val doseDescription: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(
    tableName = "supplement_log",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "localDate"])
    ]
)
data class SupplementLogEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val supplementId: String,
    val localDate: String,
    val taken: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)

@Entity(tableName = "exercise_catalog")
data class ExerciseCatalogEntity(
    @PrimaryKey val exerciseKey: String,
    val displayName: String,
    val metBase: Double,
    val category: String,
    val catalogVersion: Int
)
