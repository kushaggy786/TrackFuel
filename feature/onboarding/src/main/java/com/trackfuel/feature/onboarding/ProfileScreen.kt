package com.trackfuel.feature.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.trackfuel.core.common.UnitConverter
import com.trackfuel.domain.model.Sex
import java.time.LocalDate

@Composable
fun ProfileScreen(
    onNext: (sex: Sex, birthDate: LocalDate, heightCm: Double, weightKg: Double, isImperial: Boolean) -> Unit
) {
    var sex by remember { mutableStateOf(Sex.MALE) }
    var birthDateStr by remember { mutableStateOf("1996-07-21") }
    var isImperial by remember { mutableStateOf(false) }

    // Metric inputs
    var weightKgStr by remember { mutableStateOf("75") }
    var heightCmStr by remember { mutableStateOf("175") }

    // Imperial inputs
    var weightLbsStr by remember { mutableStateOf("165") }
    var heightFeetStr by remember { mutableStateOf("5") }
    var heightInchesStr by remember { mutableStateOf("9") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "About You",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Sex Selection
            Text("Biological Sex (for BMR calculation)", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterChip(
                    selected = sex == Sex.MALE,
                    onClick = { sex = Sex.MALE },
                    label = { Text("Male") }
                )
                FilterChip(
                    selected = sex == Sex.FEMALE,
                    onClick = { sex = Sex.FEMALE },
                    label = { Text("Female") }
                )
                FilterChip(
                    selected = sex == Sex.PREFER_NOT_TO_SAY,
                    onClick = { sex = Sex.PREFER_NOT_TO_SAY },
                    label = { Text("Prefer not to say") }
                )
            }

            if (sex == Sex.PREFER_NOT_TO_SAY) {
                Text(
                    text = "* BMR will be calculated using the male/female midpoint formula.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Birth Date
            OutlinedTextField(
                value = birthDateStr,
                onValueChange = { birthDateStr = it },
                label = { Text("Birth Date (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            // Unit System Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Unit Preference", style = MaterialTheme.typography.titleMedium)
                Row {
                    FilterChip(
                        selected = !isImperial,
                        onClick = { isImperial = false },
                        label = { Text("Metric (kg, cm)") }
                    )
                    Spacer(Modifier.width(8.dp))
                    FilterChip(
                        selected = isImperial,
                        onClick = { isImperial = true },
                        label = { Text("Imperial (lb, ft/in)") }
                    )
                }
            }

            // Height & Weight Inputs
            if (!isImperial) {
                OutlinedTextField(
                    value = heightCmStr,
                    onValueChange = { heightCmStr = it },
                    label = { Text("Height (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = weightKgStr,
                    onValueChange = { weightKgStr = it },
                    label = { Text("Current Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = heightFeetStr,
                        onValueChange = { heightFeetStr = it },
                        label = { Text("Height (ft)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    OutlinedTextField(
                        value = heightInchesStr,
                        onValueChange = { heightInchesStr = it },
                        label = { Text("Inches (in)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }
                OutlinedTextField(
                    value = weightLbsStr,
                    onValueChange = { weightLbsStr = it },
                    label = { Text("Current Weight (lbs)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }
        }

        Button(
            onClick = {
                val parsedBirthDate = runCatching { LocalDate.parse(birthDateStr) }.getOrElse { LocalDate.of(1996, 7, 21) }
                val heightCm = if (!isImperial) {
                    heightCmStr.toDoubleOrNull() ?: 175.0
                } else {
                    UnitConverter.feetInchesToCm(heightFeetStr.toIntOrNull() ?: 5, heightInchesStr.toIntOrNull() ?: 9)
                }
                val weightKg = if (!isImperial) {
                    weightKgStr.toDoubleOrNull() ?: 75.0
                } else {
                    UnitConverter.lbsToKg(weightLbsStr.toDoubleOrNull() ?: 165.0)
                }
                onNext(sex, parsedBirthDate, heightCm, weightKg, isImperial)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 16.dp)
        ) {
            Text("Next: Goals")
        }
    }
}
