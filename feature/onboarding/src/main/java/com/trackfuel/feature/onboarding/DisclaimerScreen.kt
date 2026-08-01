package com.trackfuel.feature.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DisclaimerScreen(
    onAccepted: () -> Unit
) {
    var accepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Health & Safety Disclaimer",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TrackFuel provides general wellness estimates and calorie tracking tools only. It is NOT medical advice, diagnosis, or treatment.\n\n" +
                            "Calorie needs and weight-change rates vary by individual. Consult a qualified healthcare professional before starting any diet or exercise program, especially if you are pregnant, under 18, have a history of eating disorders, or have medical conditions.\n\n" +
                            "Do not follow extreme calorie deficits. If you need nutritional therapy, seek a registered dietitian or physician.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(top = 24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { accepted = it }
                )
                Text(
                    text = "I understand and agree to the health disclaimer",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Button(
                onClick = onAccepted,
                enabled = accepted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 8.dp)
            ) {
                Text("Continue")
            }
        }
    }
}
