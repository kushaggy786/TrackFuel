package com.trackfuel.domain.engine

import com.trackfuel.domain.model.Sex

object SafetyLimits {
    const val MIN_PLANNED_DEFICIT_KCAL = 250
    const val DEFAULT_PLANNED_DEFICIT_KCAL = 500
    const val MAX_PLANNED_DEFICIT_KCAL = 1000

    val MODERATE_DEFICIT_RANGE = 300..750
    val PLANNED_SURPLUS_RANGE = 200..500
    const val DEFAULT_PLANNED_SURPLUS_KCAL = 250

    fun minSafeIntakeKcal(sex: Sex): Int = when (sex) {
        Sex.FEMALE -> 1200
        Sex.MALE -> 1500
        Sex.PREFER_NOT_TO_SAY -> 1350
    }

    const val FOOD_KCAL_SOFT_WARN = 5000
    const val FOOD_KCAL_HARD_MAX = 20000

    const val WORKOUT_KCAL_SOFT_WARN = 1500
    const val WORKOUT_KCAL_HARD_MAX = 2000

    const val WATER_GOAL_MIN_ML = 1500
    const val WATER_GOAL_MAX_ML = 4500
    const val WATER_ML_PER_KG = 33.0

    const val UNDER_FUEL_FRACTION = 0.85
    const val MIN_RECOMMENDED_AGE = 18
    const val MAX_MET_CAP = 12.0
}
