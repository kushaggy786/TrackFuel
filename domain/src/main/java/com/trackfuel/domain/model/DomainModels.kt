package com.trackfuel.domain.model

import java.time.LocalDate

enum class Sex {
    MALE,
    FEMALE,
    PREFER_NOT_TO_SAY
}

enum class GoalType {
    LOSE_WEIGHT,
    MAINTAIN,
    GAIN
}

enum class ActivityLevel(val multiplier: Double) {
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    VERY_ACTIVE(1.725),
    EXTRA_ACTIVE(1.9)
}

enum class MealSlot {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACKS
}

enum class Intensity(val factor: Double) {
    LOW(0.85),
    MOD(1.0),
    HIGH(1.15)
}

enum class CaloriesSource {
    ESTIMATED,
    MANUAL
}

enum class AchievementCode {
    NONE,
    UNDER_FUELED,
    OVER_BUDGET,
    NEAR_MISS,
    ON_GOAL,
    STRONG
}

data class UserProfile(
    val id: String,
    val sex: Sex,
    val birthDate: LocalDate,
    val heightCm: Double,
    val weightKg: Double,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val targetWeightKg: Double?,
    val targetDeltaKcal: Int,
    val targetProteinGrams: Int,
    val units: String = "METRIC",
    val timezoneId: String = "UTC",
    val syncTimezoneWithDevice: Boolean = true,
    val countExerciseSeparately: Boolean = true,
    val formulaId: String = "mifflin_st_jeor_v1",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class FoodEntry(
    val id: String,
    val userId: String,
    val localDate: String,
    val name: String,
    val caloriesPerUnit: Double,
    val portionQty: Double,
    val portionUnit: String,
    val caloriesTotal: Int,
    val proteinGrams: Int = 0,
    val mealSlot: MealSlot,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class WorkoutEntry(
    val id: String,
    val userId: String,
    val localDate: String,
    val exerciseKey: String,
    val displayName: String,
    val durationMin: Int,
    val intensity: Intensity,
    val metUsed: Double,
    val caloriesBurned: Int,
    val caloriesSource: CaloriesSource = CaloriesSource.ESTIMATED,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class WaterEntry(
    val id: String,
    val userId: String,
    val localDate: String,
    val amountMl: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class BodyWeightLog(
    val id: String,
    val userId: String,
    val localDate: String,
    val weightKg: Double,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class DaySummary(
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
    val achievementCode: AchievementCode,
    val underFueled: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class SupplementDefinition(
    val id: String,
    val userId: String,
    val name: String,
    val doseDescription: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class SupplementLog(
    val id: String,
    val userId: String,
    val supplementId: String,
    val localDate: String,
    val taken: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)

data class ExerciseCatalogItem(
    val exerciseKey: String,
    val displayName: String,
    val metBase: Double,
    val category: String,
    val catalogVersion: Int = 1
)
