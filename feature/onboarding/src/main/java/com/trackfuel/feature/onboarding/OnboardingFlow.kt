package com.trackfuel.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnboardingFlow(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val step by viewModel.step.collectAsState()

    when (val currentStep = step) {
        is OnboardingStep.Disclaimer -> {
            DisclaimerScreen(
                onAccepted = { viewModel.onDisclaimerAccepted() }
            )
        }
        is OnboardingStep.Profile -> {
            ProfileScreen(
                onNext = { sex, birthDate, heightCm, weightKg, isImperial ->
                    viewModel.onProfileSubmitted(sex, birthDate, heightCm, weightKg, isImperial)
                }
            )
        }
        is OnboardingStep.Goals -> {
            GoalsScreen(
                weightKg = currentStep.weightKg,
                onNext = { goalType, activityLevel, targetDeltaKcal, targetProteinGrams ->
                    viewModel.onGoalsSubmitted(goalType, activityLevel, targetDeltaKcal, targetProteinGrams)
                }
            )
        }
        is OnboardingStep.Summary -> {
            SummaryScreen(
                profile = currentStep.userProfile,
                onComplete = { viewModel.onOnboardingCompleted(onFinished) }
            )
        }
    }
}
