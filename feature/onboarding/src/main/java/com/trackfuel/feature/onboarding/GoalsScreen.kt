package com.trackfuel.feature.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.trackfuel.domain.engine.SafetyLimits
import com.trackfuel.domain.model.ActivityLevel
import com.trackfuel.domain.model.GoalType
import kotlin.math.roundToInt

@Composable
fun GoalsScreen(
    weightKg: Double,
    onNext: (goalType: GoalType, activityLevel: ActivityLevel, targetDeltaKcal: Int, targetProteinGrams: Int) -> Unit
) {
    var goalType by remember { mutableStateOf(GoalType.LOSE_WEIGHT) }
    var activityLevel by remember { mutableStateOf(ActivityLevel.MODERATE) }

    var targetDeltaKcal by remember { mutableIntStateOf(SafetyLimits.DEFAULT_PLANNED_DEFICIT_KCAL) }
    var targetProteinGramsStr by remember { mutableStateOf((weightKg * 2.0).roundToInt().toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Your Fitness Goals",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Goal Type
            Text("Goal", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterChip(
                    selected = goalType == GoalType.LOSE_WEIGHT,
                    onClick = {
                        goalType = GoalType.LOSE_WEIGHT
                        targetDeltaKcal = SafetyLimits.DEFAULT_PLANNED_DEFICIT_KCAL
                    },
                    label = { Text("Lose Weight") }
                )
                FilterChip(
                    selected = goalType == GoalType.MAINTAIN,
                    onClick = {
                        goalType = GoalType.MAINTAIN
                        targetDeltaKcal = 0
                    },
                    label = { Text("Maintain") }
                )
                FilterChip(
                    selected = goalType == GoalType.GAIN,
                    onClick = {
                        goalType = GoalType.GAIN
                        targetDeltaKcal = SafetyLimits.DEFAULT_PLANNED_SURPLUS_KCAL
                    },
                    label = { Text("Gain Muscle") }
                )
            }

            // Target Delta Slider
            if (goalType != GoalType.MAINTAIN) {
                val labelText = if (goalType == GoalType.LOSE_WEIGHT) "Planned Daily Deficit" else "Planned Daily Surplus"
                val minVal = if (goalType == GoalType.LOSE_WEIGHT) SafetyLimits.MIN_PLANNED_DEFICIT_KCAL.toFloat() else SafetyLimits.PLANNED_SURPLUS_RANGE.first.toFloat()
                val maxVal = if (goalType == GoalType.LOSE_WEIGHT) SafetyLimits.MAX_PLANNED_DEFICIT_KCAL.toFloat() else SafetyLimits.PLANNED_SURPLUS_RANGE.last.toFloat()

                Text(
                    text = "$labelText: $targetDeltaKcal kcal/day",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Slider(
                    value = targetDeltaKcal.toFloat(),
                    onValueChange = { targetDeltaKcal = it.roundToInt() },
                    valueRange = minVal..maxVal,
                    steps = 5,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Activity Level
            Text("Activity Level", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                ActivityLevel.entries.forEach { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = activityLevel == level,
                            onClick = { activityLevel = level }
                        )
                        Text(
                            text = "${level.name.replace("_", " ")} (x${level.multiplier})",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            // Protein Target
            OutlinedTextField(
                value = targetProteinGramsStr,
                onValueChange = { targetProteinGramsStr = it },
                label = { Text("Daily Protein Target (grams)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
        }

        Button(
            onClick = {
                val proteinTarget = targetProteinGramsStr.toIntOrNull() ?: 150
                onNext(goalType, activityLevel, targetDeltaKcal, proteinTarget)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 16.dp)
        ) {
            Text("Next: TDEE Summary")
        }
    }
}
