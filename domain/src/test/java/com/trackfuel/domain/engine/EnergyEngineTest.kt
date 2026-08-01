package com.trackfuel.domain.engine

import com.trackfuel.domain.model.ActivityLevel
import com.trackfuel.domain.model.GoalType
import com.trackfuel.domain.model.Intensity
import com.trackfuel.domain.model.Sex
import com.trackfuel.domain.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class EnergyEngineTest {

    private val testDate = LocalDate.of(2026, 7, 21)

    @Test
    fun testGoldenVector1_Male30y_75kg_180cm_Sedentary() {
        // Given
        val profile = UserProfile(
            id = "user1",
            sex = Sex.MALE,
            birthDate = LocalDate.of(1996, 7, 21), // 30 years old
            heightCm = 180.0,
            weightKg = 75.0,
            activityLevel = ActivityLevel.SEDENTARY, // x1.2
            goalType = GoalType.LOSE_WEIGHT,
            targetWeightKg = 70.0,
            targetDeltaKcal = 500,
            targetProteinGrams = 150
        )

        // BMR = 10(75) + 6.25(180) - 5(30) + 5 = 750 + 1125 - 150 + 5 = 1730
        // TDEE = round(1730 * 1.2) = 2076

        // When
        val result = CalorieEngine.dayResult(
            profile = profile,
            consumedKcal = 1800,
            consumedProteinGrams = 140,
            exerciseBurnKcal = 0,
            onDate = testDate
        )

        // Then
        assertEquals(1730, result.bmr)
        assertEquals(2076, result.tdee)
        assertEquals(2076, result.totalBurned)
        assertEquals(276, result.netCalories) // 2076 - 1800 = +276
        assertEquals(1576, result.calorieTarget) // max(2076 - 500, 1500) = 1576
        assertEquals(-224, result.budgetRemaining) // 1576 - 1800 = -224
    }

    @Test
    fun testGoldenVector2_Female30y_65kg_165cm_Moderate() {
        // Given
        val profile = UserProfile(
            id = "user2",
            sex = Sex.FEMALE,
            birthDate = LocalDate.of(1996, 7, 21),
            heightCm = 165.0,
            weightKg = 65.0,
            activityLevel = ActivityLevel.MODERATE, // x1.55
            goalType = GoalType.LOSE_WEIGHT,
            targetWeightKg = 60.0,
            targetDeltaKcal = 500,
            targetProteinGrams = 130
        )

        // BMR = 10(65) + 6.25(165) - 5(30) - 161 = 650 + 1031.25 - 150 - 161 = 1370.25 -> 1370
        // TDEE = round(1370.25 * 1.55) = 2124

        // When
        val result = CalorieEngine.dayResult(
            profile = profile,
            consumedKcal = 1500,
            consumedProteinGrams = 120,
            exerciseBurnKcal = 0,
            onDate = testDate
        )

        assertEquals(1370, result.bmr)
        assertEquals(2124, result.tdee)
    }

    @Test
    fun testGoldenVector3_Male40y_90kg_175cm_Light() {
        // Given
        val profile = UserProfile(
            id = "user3",
            sex = Sex.MALE,
            birthDate = LocalDate.of(1986, 7, 21), // 40 years old
            heightCm = 175.0,
            weightKg = 90.0,
            activityLevel = ActivityLevel.LIGHT, // x1.375
            goalType = GoalType.LOSE_WEIGHT,
            targetWeightKg = 80.0,
            targetDeltaKcal = 500,
            targetProteinGrams = 160
        )

        // BMR = 10(90) + 6.25(175) - 5(40) + 5 = 900 + 1093.75 - 200 + 5 = 1798.75 -> 1799
        // TDEE = round(1798.75 * 1.375) = 2473

        // When
        val result = CalorieEngine.dayResult(
            profile = profile,
            consumedKcal = 1900,
            consumedProteinGrams = 150,
            exerciseBurnKcal = 0,
            onDate = testDate
        )

        assertEquals(1799, result.bmr)
        assertEquals(2473, result.tdee)
    }

    @Test
    fun testMetFixture_75kg_BriskWalk_45min() {
        // Given 75 kg, MET 3.5, 45 min MOD -> 3.5 * 75 * 0.75 = 196.875 -> 197 kcal
        val burn = WorkoutCalculator.calculateBurn(
            metBase = 3.5,
            weightKg = 75.0,
            durationMin = 45,
            intensity = Intensity.MOD
        )
        assertEquals(197, burn)
    }

    @Test
    fun testProperty_NetCaloriesIdentity() {
        val profile = UserProfile(
            id = "test",
            sex = Sex.MALE,
            birthDate = LocalDate.of(1995, 1, 1),
            heightCm = 175.0,
            weightKg = 70.0,
            activityLevel = ActivityLevel.MODERATE,
            goalType = GoalType.LOSE_WEIGHT,
            targetWeightKg = 65.0,
            targetDeltaKcal = 500,
            targetProteinGrams = 140
        )

        val result = CalorieEngine.dayResult(
            profile = profile,
            consumedKcal = 1600,
            consumedProteinGrams = 140,
            exerciseBurnKcal = 300,
            onDate = testDate
        )

        // Property check: netCalories == totalBurned - consumed
        assertEquals(result.totalBurned - result.consumed, result.netCalories)
        // Property check: budgetRemaining == (calorieTarget + exerciseBonus) - consumed
        assertEquals((result.calorieTarget + result.exerciseBonus) - result.consumed, result.budgetRemaining)
    }
}
