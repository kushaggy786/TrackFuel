package com.trackfuel.domain.engine

import com.trackfuel.domain.model.AchievementCode
import com.trackfuel.domain.model.GoalType

object ProgressScale {

    fun calculateScore(budgetRemaining: Int, allowance: Int): Double {
        return budgetRemaining.toDouble() / allowance.coerceAtLeast(1)
    }

    fun achievement(
        goalType: GoalType,
        consumed: Int,
        budgetRemaining: Int,
        allowance: Int,
        underFueled: Boolean
    ): AchievementCode {
        if (consumed <= 0) return AchievementCode.NONE
        if (underFueled) return AchievementCode.UNDER_FUELED

        val score = calculateScore(budgetRemaining, allowance)

        return when (goalType) {
            GoalType.LOSE_WEIGHT, GoalType.MAINTAIN -> when {
                score < -0.20 -> AchievementCode.OVER_BUDGET
                score < 0.0 -> AchievementCode.NEAR_MISS
                score <= 0.25 -> AchievementCode.ON_GOAL
                else -> AchievementCode.STRONG
            }
            GoalType.GAIN -> {
                val gainScore = -score
                when {
                    gainScore < -0.20 -> AchievementCode.OVER_BUDGET
                    gainScore < 0.0 -> AchievementCode.NEAR_MISS
                    gainScore <= 0.25 -> AchievementCode.ON_GOAL
                    else -> AchievementCode.STRONG
                }
            }
        }
    }

    fun isOnTrack(code: AchievementCode, goalType: GoalType): Boolean {
        return code == AchievementCode.ON_GOAL || code == AchievementCode.STRONG
    }
}
