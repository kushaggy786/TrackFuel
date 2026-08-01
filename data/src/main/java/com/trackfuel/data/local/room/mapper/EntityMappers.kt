package com.trackfuel.data.local.room.mapper

import com.trackfuel.data.local.room.entity.*
import com.trackfuel.domain.model.*
import java.time.LocalDate

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    sex = Sex.valueOf(sex),
    birthDate = LocalDate.parse(birthDate),
    heightCm = heightCm,
    weightKg = weightKg,
    activityLevel = ActivityLevel.valueOf(activityLevel),
    goalType = GoalType.valueOf(goalType),
    targetWeightKg = targetWeightKg,
    targetDeltaKcal = targetDeltaKcal,
    targetProteinGrams = targetProteinGrams,
    units = units,
    timezoneId = timezoneId,
    syncTimezoneWithDevice = syncTimezoneWithDevice,
    countExerciseSeparately = countExerciseSeparately,
    formulaId = formulaId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun UserProfile.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    sex = sex.name,
    birthDate = birthDate.toString(),
    heightCm = heightCm,
    weightKg = weightKg,
    activityLevel = activityLevel.name,
    goalType = goalType.name,
    targetWeightKg = targetWeightKg,
    targetDeltaKcal = targetDeltaKcal,
    targetProteinGrams = targetProteinGrams,
    units = units,
    timezoneId = timezoneId,
    syncTimezoneWithDevice = syncTimezoneWithDevice,
    countExerciseSeparately = countExerciseSeparately,
    formulaId = formulaId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun FoodEntryEntity.toDomain(): FoodEntry = FoodEntry(
    id = id,
    userId = userId,
    localDate = localDate,
    name = name,
    caloriesPerUnit = caloriesPerUnit,
    portionQty = portionQty,
    portionUnit = portionUnit,
    caloriesTotal = caloriesTotal,
    proteinGrams = proteinGrams,
    mealSlot = MealSlot.valueOf(mealSlot),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun FoodEntry.toEntity(): FoodEntryEntity = FoodEntryEntity(
    id = id,
    userId = userId,
    localDate = localDate,
    name = name,
    caloriesPerUnit = caloriesPerUnit,
    portionQty = portionQty,
    portionUnit = portionUnit,
    caloriesTotal = caloriesTotal,
    proteinGrams = proteinGrams,
    mealSlot = mealSlot.name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun WorkoutEntryEntity.toDomain(): WorkoutEntry = WorkoutEntry(
    id = id,
    userId = userId,
    localDate = localDate,
    exerciseKey = exerciseKey,
    displayName = displayName,
    durationMin = durationMin,
    intensity = Intensity.valueOf(intensity),
    metUsed = metUsed,
    caloriesBurned = caloriesBurned,
    caloriesSource = CaloriesSource.valueOf(caloriesSource),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun WorkoutEntry.toEntity(): WorkoutEntryEntity = WorkoutEntryEntity(
    id = id,
    userId = userId,
    localDate = localDate,
    exerciseKey = exerciseKey,
    displayName = displayName,
    durationMin = durationMin,
    intensity = intensity.name,
    metUsed = metUsed,
    caloriesBurned = caloriesBurned,
    caloriesSource = caloriesSource.name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun WaterEntryEntity.toDomain(): WaterEntry = WaterEntry(
    id = id,
    userId = userId,
    localDate = localDate,
    amountMl = amountMl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun WaterEntry.toEntity(): WaterEntryEntity = WaterEntryEntity(
    id = id,
    userId = userId,
    localDate = localDate,
    amountMl = amountMl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun BodyWeightLogEntity.toDomain(): BodyWeightLog = BodyWeightLog(
    id = id,
    userId = userId,
    localDate = localDate,
    weightKg = weightKg,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun BodyWeightLog.toEntity(): BodyWeightLogEntity = BodyWeightLogEntity(
    id = id,
    userId = userId,
    localDate = localDate,
    weightKg = weightKg,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun DaySummaryEntity.toDomain(): DaySummary = DaySummary(
    userId = userId,
    localDate = localDate,
    consumedKcal = consumedKcal,
    consumedProteinGrams = consumedProteinGrams,
    exerciseBurnKcal = exerciseBurnKcal,
    bmrKcal = bmrKcal,
    tdeeKcal = tdeeKcal,
    totalBurnedKcal = totalBurnedKcal,
    netKcal = netKcal,
    calorieTargetKcal = calorieTargetKcal,
    budgetRemainingKcal = budgetRemainingKcal,
    waterMl = waterMl,
    achievementCode = AchievementCode.valueOf(achievementCode),
    underFueled = underFueled,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun DaySummary.toEntity(): DaySummaryEntity = DaySummaryEntity(
    userId = userId,
    localDate = localDate,
    consumedKcal = consumedKcal,
    consumedProteinGrams = consumedProteinGrams,
    exerciseBurnKcal = exerciseBurnKcal,
    bmrKcal = bmrKcal,
    tdeeKcal = tdeeKcal,
    totalBurnedKcal = totalBurnedKcal,
    netKcal = netKcal,
    calorieTargetKcal = calorieTargetKcal,
    budgetRemainingKcal = budgetRemainingKcal,
    waterMl = waterMl,
    achievementCode = achievementCode.name,
    underFueled = underFueled,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun SupplementDefinitionEntity.toDomain(): SupplementDefinition = SupplementDefinition(
    id = id,
    userId = userId,
    name = name,
    doseDescription = doseDescription,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun SupplementDefinition.toEntity(): SupplementDefinitionEntity = SupplementDefinitionEntity(
    id = id,
    userId = userId,
    name = name,
    doseDescription = doseDescription,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun SupplementLogEntity.toDomain(): SupplementLog = SupplementLog(
    id = id,
    userId = userId,
    supplementId = supplementId,
    localDate = localDate,
    taken = taken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun SupplementLog.toEntity(): SupplementLogEntity = SupplementLogEntity(
    id = id,
    userId = userId,
    supplementId = supplementId,
    localDate = localDate,
    taken = taken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun ExerciseCatalogEntity.toDomain(): ExerciseCatalogItem = ExerciseCatalogItem(
    exerciseKey = exerciseKey,
    displayName = displayName,
    metBase = metBase,
    category = category,
    catalogVersion = catalogVersion
)

fun ExerciseCatalogItem.toEntity(): ExerciseCatalogEntity = ExerciseCatalogEntity(
    exerciseKey = exerciseKey,
    displayName = displayName,
    metBase = metBase,
    category = category,
    catalogVersion = catalogVersion
)
