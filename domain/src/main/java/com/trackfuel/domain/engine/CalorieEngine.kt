package com.trackfuel.domain.engine

import com.trackfuel.core.common.calculateAge
import com.trackfuel.domain.model.AchievementCode
import com.trackfuel.domain.model.GoalType
import com.trackfuel.domain.model.UserProfile
import java.time.LocalDate
import kotlin.math.roundToInt

data class DayEnergyResult(
    val bmr: Int,
    val tdee: Int,
    val exerciseBurn: Int,
    val exerciseBonus: Int,
    val totalBurned: Int,
    val consumed: Int,
    val consumedProteinGrams: Int,
    val netCalories: Int,
    val calorieTarget: Int,
    val budgetRemaining: Int,
    val underFueled: Boolean,
    val achievement: AchievementCode,
    val onTrack: Boolean
)

object CalorieEngine {

    fun dayResult(
        profile: UserProfile,
        consumedKcal: Int,
        consumedProteinGrams: Int,
        exerciseBurnKcal: Int,
        onDate: LocalDate,
        bmrFormula: BmrFormula = MifflinStJeorFormula()
    ): DayEnergyResult {
        val age = calculateAge(profile.birthDate, onDate)
        val rawBmr = bmrFormula.calculateBmr(profile.sex, profile.weightKg, profile.heightCm, age)
        val tdee = (rawBmr * profile.activityLevel.multiplier).roundToInt().coerceAtLeast(1)

        val exerciseBonus = if (profile.countExerciseSeparately) exerciseBurnKcal else 0
        val totalBurned = tdee + exerciseBonus
        val netCalories = totalBurned - consumedKcal

        val minSafe = SafetyLimits.minSafeIntakeKcal(profile.sex)
        val delta = profile.targetDeltaKcal.coerceIn(
            when (profile.goalType) {
                GoalType.LOSE_WEIGHT -> SafetyLimits.MIN_PLANNED_DEFICIT_KCAL..SafetyLimits.MAX_PLANNED_DEFICIT_KCAL
                GoalType.MAINTAIN -> 0..0
                GoalType.GAIN -> SafetyLimits.PLANNED_SURPLUS_RANGE
            }
        )

        val calorieTarget = when (profile.goalType) {
            GoalType.LOSE_WEIGHT -> (tdee - delta).coerceAtLeast(minSafe)
            GoalType.MAINTAIN -> tdee
            GoalType.GAIN -> tdee + delta
        }

        val allowance = calorieTarget + exerciseBonus
        val budgetRemaining = allowance - consumedKcal
        val underFueled = consumedKcal > 0 && consumedKcal < (minSafe * SafetyLimits.UNDER_FUEL_FRACTION).roundToInt()

        val achievement = ProgressScale.achievement(
            goalType = profile.goalType,
            consumed = consumedKcal,
            budgetRemaining = budgetRemaining,
            allowance = allowance,
            underFueled = underFueled
        )

        return DayEnergyResult(
            bmr = rawBmr.roundToInt(),
            tdee = tdee,
            exerciseBurn = exerciseBurnKcal,
            exerciseBonus = exerciseBonus,
            totalBurned = totalBurned,
            consumed = consumedKcal,
            consumedProteinGrams = consumedProteinGrams,
            netCalories = netCalories,
            calorieTarget = calorieTarget,
            budgetRemaining = budgetRemaining,
            underFueled = underFueled,
            achievement = achievement,
            onTrack = ProgressScale.isOnTrack(achievement, profile.goalType)
        )
    }
}
