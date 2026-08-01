package com.trackfuel.core.common

import kotlin.math.roundToInt

object UnitConverter {
    fun kgToLbs(kg: Double): Double = kg * 2.20462
    fun lbsToKg(lbs: Double): Double = lbs / 2.20462

    fun cmToInches(cm: Double): Double = cm / 2.54
    fun inchesToCm(inches: Double): Double = inches * 2.54

    fun cmToFeetInches(cm: Double): Pair<Int, Int> {
        val totalInches = (cm / 2.54).roundToInt()
        val feet = totalInches / 12
        val inches = totalInches % 12
        return Pair(feet, inches)
    }

    fun feetInchesToCm(feet: Int, inches: Int): Double {
        val totalInches = feet * 12 + inches
        return totalInches * 2.54
    }

    fun mlToFlOz(ml: Int): Double = ml / 29.5735
    fun flOzToMl(flOz: Double): Int = (flOz * 29.5735).roundToInt()
}
