package com.trackfuel.domain.engine

import com.trackfuel.domain.model.Intensity
import kotlin.math.min
import kotlin.math.roundToInt

object WorkoutCalculator {

    fun calculateBurn(
        metBase: Double,
        weightKg: Double,
        durationMin: Int,
        intensity: Intensity
    ): Int {
        val effectiveMet = min(metBase * intensity.factor, SafetyLimits.MAX_MET_CAP)
        val hours = durationMin / 60.0
        return (effectiveMet * weightKg * hours).roundToInt()
    }
}

object WaterGoalCalculator {

    fun calculateGoalMl(weightKg: Double, activityLevelMultiplier: Double = 1.0): Int {
        val baseMl = weightKg * SafetyLimits.WATER_ML_PER_KG
        val adjustedMl = baseMl * activityLevelMultiplier
        val rounded = ((adjustedMl / 50.0).roundToInt() * 50)
        return rounded.coerceIn(SafetyLimits.WATER_GOAL_MIN_ML, SafetyLimits.WATER_GOAL_MAX_ML)
    }
}

object WeightTrendCalculator {

    fun calculateTrendKgPerWeek(weightLogs: List<Pair<Int, Double>>): Double? {
        if (weightLogs.size < 4) return null

        val n = weightLogs.size
        val sumX = weightLogs.sumOf { it.first.toDouble() }
        val sumY = weightLogs.sumOf { it.second }
        val sumXY = weightLogs.sumOf { it.first.toDouble() * it.second }
        val sumX2 = weightLogs.sumOf { it.first.toDouble() * it.first.toDouble() }

        val denominator = (n * sumX2 - sumX * sumX)
        if (denominator == 0.0) return 0.0

        val slopePerDay = (n * sumXY - sumX * sumY) / denominator
        return slopePerDay * 7.0
    }
}
