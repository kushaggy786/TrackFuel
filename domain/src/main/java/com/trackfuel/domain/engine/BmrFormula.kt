package com.trackfuel.domain.engine

import com.trackfuel.domain.model.Sex

interface BmrFormula {
    val formulaId: String
    fun calculateBmr(sex: Sex, weightKg: Double, heightCm: Double, ageYears: Int): Double
}

class MifflinStJeorFormula : BmrFormula {
    override val formulaId: String = "mifflin_st_jeor_v1"

    override fun calculateBmr(sex: Sex, weightKg: Double, heightCm: Double, ageYears: Int): Double {
        val base = 10.0 * weightKg + 6.25 * heightCm - 5.0 * ageYears
        return when (sex) {
            Sex.MALE -> base + 5.0
            Sex.FEMALE -> base - 161.0
            Sex.PREFER_NOT_TO_SAY -> base - 78.0 // Midpoint of male (+5) and female (-161)
        }
    }
}
