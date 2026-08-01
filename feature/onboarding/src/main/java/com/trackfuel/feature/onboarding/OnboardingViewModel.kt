package com.trackfuel.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfuel.data.local.datastore.SettingsDataSource
import com.trackfuel.domain.model.*
import com.trackfuel.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

sealed interface OnboardingStep {
    object Disclaimer : OnboardingStep
    object Profile : OnboardingStep
    data class Goals(val weightKg: Double) : OnboardingStep
    data class Summary(val userProfile: UserProfile) : OnboardingStep
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val settingsDataSource: SettingsDataSource
) : ViewModel() {

    private val _step = MutableStateFlow<OnboardingStep>(OnboardingStep.Disclaimer)
    val step: StateFlow<OnboardingStep> = _step.asStateFlow()

    private var draftSex: Sex = Sex.MALE
    private var draftBirthDate: LocalDate = LocalDate.of(1996, 7, 21)
    private var draftHeightCm: Double = 175.0
    private var draftWeightKg: Double = 75.0
    private var draftIsImperial: Boolean = false

    fun onDisclaimerAccepted() {
        viewModelScope.launch {
            settingsDataSource.setDisclaimerAcceptedVersion(1)
            _step.value = OnboardingStep.Profile
        }
    }

    fun onProfileSubmitted(
        sex: Sex,
        birthDate: LocalDate,
        heightCm: Double,
        weightKg: Double,
        isImperial: Boolean
    ) {
        draftSex = sex
        draftBirthDate = birthDate
        draftHeightCm = heightCm
        draftWeightKg = weightKg
        draftIsImperial = isImperial
        viewModelScope.launch {
            if (isImperial) {
                settingsDataSource.setUnitsPreference("IMPERIAL")
            } else {
                settingsDataSource.setUnitsPreference("METRIC")
            }
            _step.value = OnboardingStep.Goals(weightKg)
        }
    }

    fun onGoalsSubmitted(
        goalType: GoalType,
        activityLevel: ActivityLevel,
        targetDeltaKcal: Int,
        targetProteinGrams: Int
    ) {
        val profile = UserProfile(
            id = UUID.randomUUID().toString(),
            sex = draftSex,
            birthDate = draftBirthDate,
            heightCm = draftHeightCm,
            weightKg = draftWeightKg,
            activityLevel = activityLevel,
            goalType = goalType,
            targetWeightKg = if (goalType == GoalType.LOSE_WEIGHT) draftWeightKg - 5.0 else if (goalType == GoalType.GAIN) draftWeightKg + 5.0 else draftWeightKg,
            targetDeltaKcal = targetDeltaKcal,
            targetProteinGrams = targetProteinGrams,
            units = if (draftIsImperial) "IMPERIAL" else "METRIC"
        )

        viewModelScope.launch {
            userProfileRepository.upsert(profile)
            _step.value = OnboardingStep.Summary(profile)
        }
    }

    fun onOnboardingCompleted(onComplete: () -> Unit) {
        viewModelScope.launch {
            settingsDataSource.setOnboardingComplete(true)
            onComplete()
        }
    }
}
