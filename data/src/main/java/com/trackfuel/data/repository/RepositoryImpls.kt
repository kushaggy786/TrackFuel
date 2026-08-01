package com.trackfuel.data.repository

import com.trackfuel.core.common.DateProvider
import com.trackfuel.data.local.room.dao.*
import com.trackfuel.data.local.room.entity.ExerciseCatalogEntity
import com.trackfuel.data.local.room.mapper.*
import com.trackfuel.domain.engine.CalorieEngine
import com.trackfuel.domain.model.*
import com.trackfuel.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val userProfileDao: UserProfileDao,
    private val foodEntryDao: FoodEntryDao,
    private val workoutEntryDao: WorkoutEntryDao,
    private val waterEntryDao: WaterEntryDao,
    private val bodyWeightLogDao: BodyWeightLogDao,
    private val daySummaryDao: DaySummaryDao,
    private val supplementDao: SupplementDao
) : UserProfileRepository {

    override fun observeProfile(): Flow<UserProfile?> =
        userProfileDao.observeProfile().map { it?.toDomain() }

    override suspend fun getProfile(): UserProfile? =
        userProfileDao.getProfile()?.toDomain()

    override suspend fun upsert(profile: UserProfile) {
        userProfileDao.upsert(profile.toEntity())
    }

    override suspend fun deleteAllUserData() {
        userProfileDao.deleteAll()
        foodEntryDao.deleteAll()
        workoutEntryDao.deleteAll()
        waterEntryDao.deleteAll()
        bodyWeightLogDao.deleteAll()
        daySummaryDao.deleteAll()
        supplementDao.deleteAllDefinitions()
        supplementDao.deleteAllLogs()
    }
}

class FoodEntryRepositoryImpl(
    private val foodEntryDao: FoodEntryDao,
    private val daySummaryRepository: DaySummaryRepository
) : FoodEntryRepository {

    override fun observeForDate(userId: String, localDate: String): Flow<List<FoodEntry>> =
        foodEntryDao.observeForDate(userId, localDate).map { list -> list.map { it.toDomain() } }

    override suspend fun upsert(entry: FoodEntry) {
        foodEntryDao.upsert(entry.toEntity())
        daySummaryRepository.recompute(entry.userId, entry.localDate)
    }

    override suspend fun softDelete(id: String) {
        foodEntryDao.softDelete(id)
    }
}

class WorkoutRepositoryImpl(
    private val workoutEntryDao: WorkoutEntryDao,
    private val daySummaryRepository: DaySummaryRepository
) : WorkoutRepository {

    override fun observeForDate(userId: String, localDate: String): Flow<List<WorkoutEntry>> =
        workoutEntryDao.observeForDate(userId, localDate).map { list -> list.map { it.toDomain() } }

    override suspend fun upsert(entry: WorkoutEntry) {
        workoutEntryDao.upsert(entry.toEntity())
        daySummaryRepository.recompute(entry.userId, entry.localDate)
    }

    override suspend fun softDelete(id: String) {
        workoutEntryDao.softDelete(id)
    }
}

class WaterRepositoryImpl(
    private val waterEntryDao: WaterEntryDao,
    private val daySummaryRepository: DaySummaryRepository
) : WaterRepository {

    override fun observeTotalMl(userId: String, localDate: String): Flow<Int> =
        waterEntryDao.observeForDate(userId, localDate).map { list -> list.sumOf { it.amountMl } }

    override suspend fun add(entry: WaterEntry) {
        waterEntryDao.upsert(entry.toEntity())
        daySummaryRepository.recompute(entry.userId, entry.localDate)
    }

    override suspend fun softDelete(id: String) {
        waterEntryDao.softDelete(id)
    }
}

class BodyWeightRepositoryImpl(
    private val bodyWeightLogDao: BodyWeightLogDao,
    private val userProfileDao: UserProfileDao
) : BodyWeightRepository {

    override fun observeAll(userId: String): Flow<List<BodyWeightLog>> =
        bodyWeightLogDao.observeAll(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun getRecent14Days(userId: String): List<BodyWeightLog> =
        bodyWeightLogDao.getRecent14Days(userId).map { it.toDomain() }

    override suspend fun add(entry: BodyWeightLog) {
        bodyWeightLogDao.upsert(entry.toEntity())
        // Auto-update profile current weight
        userProfileDao.getProfile()?.let { profile ->
            userProfileDao.upsert(profile.copy(weightKg = entry.weightKg, updatedAt = System.currentTimeMillis()))
        }
    }

    override suspend fun softDelete(id: String) {
        bodyWeightLogDao.softDelete(id)
    }
}

class DaySummaryRepositoryImpl(
    private val userProfileDao: UserProfileDao,
    private val foodEntryDao: FoodEntryDao,
    private val workoutEntryDao: WorkoutEntryDao,
    private val waterEntryDao: WaterEntryDao,
    private val daySummaryDao: DaySummaryDao,
    private val dateProvider: DateProvider
) : DaySummaryRepository {

    override fun observeForDate(userId: String, localDate: String): Flow<DaySummary?> =
        daySummaryDao.observeForDate(userId, localDate).map { it?.toDomain() }

    override fun observeRange(userId: String, fromDate: String, toDate: String): Flow<List<DaySummary>> =
        daySummaryDao.observeRange(userId, fromDate, toDate).map { list -> list.map { it.toDomain() } }

    override suspend fun recompute(userId: String, localDate: String) {
        val profile = userProfileDao.getProfile()?.toDomain() ?: return
        val foods = foodEntryDao.getForDate(userId, localDate).map { it.toDomain() }
        val workouts = workoutEntryDao.getForDate(userId, localDate).map { it.toDomain() }
        val waterTotal = waterEntryDao.getForDate(userId, localDate).sumOf { it.amountMl }

        val consumedKcal = foods.sumOf { it.caloriesTotal }
        val consumedProtein = foods.sumOf { it.proteinGrams }
        val exerciseBurnKcal = workouts.sumOf { it.caloriesBurned }
        val parsedDate = dateProvider.today().toLocalDate()

        val result = CalorieEngine.dayResult(
            profile = profile,
            consumedKcal = consumedKcal,
            consumedProteinGrams = consumedProtein,
            exerciseBurnKcal = exerciseBurnKcal,
            onDate = parsedDate
        )

        val summary = DaySummary(
            userId = userId,
            localDate = localDate,
            consumedKcal = result.consumed,
            consumedProteinGrams = result.consumedProteinGrams,
            exerciseBurnKcal = result.exerciseBurn,
            bmrKcal = result.bmr,
            tdeeKcal = result.tdee,
            totalBurnedKcal = result.totalBurned,
            netKcal = result.netCalories,
            calorieTargetKcal = result.calorieTarget,
            budgetRemainingKcal = result.budgetRemaining,
            waterMl = waterTotal,
            achievementCode = result.achievement,
            underFueled = result.underFueled,
            updatedAt = System.currentTimeMillis()
        )

        daySummaryDao.upsert(summary.toEntity())
    }
}

class SupplementRepositoryImpl(
    private val supplementDao: SupplementDao
) : SupplementRepository {

    override fun observeDefinitions(userId: String): Flow<List<SupplementDefinition>> =
        supplementDao.observeDefinitions(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun upsertDefinition(definition: SupplementDefinition) {
        supplementDao.upsertDefinition(definition.toEntity())
    }

    override suspend fun softDeleteDefinition(id: String) {
        supplementDao.softDeleteDefinition(id)
    }

    override fun observeLogsForDate(userId: String, localDate: String): Flow<List<SupplementLog>> =
        supplementDao.observeLogsForDate(userId, localDate).map { list -> list.map { it.toDomain() } }

    override suspend fun upsertLog(log: SupplementLog) {
        supplementDao.upsertLog(log.toEntity())
    }
}

class ExerciseCatalogRepositoryImpl(
    private val exerciseCatalogDao: ExerciseCatalogDao
) : ExerciseCatalogRepository {

    private val seedExercises = listOf(
        ExerciseCatalogEntity("walk_brisk", "Walking (brisk)", 3.5, "CARDIO", 1),
        ExerciseCatalogEntity("run_mod", "Running (moderate)", 8.0, "CARDIO", 1),
        ExerciseCatalogEntity("cycle_mod", "Cycling (moderate)", 6.8, "CARDIO", 1),
        ExerciseCatalogEntity("swim_mod", "Swimming", 6.0, "CARDIO", 1),
        ExerciseCatalogEntity("gym_gen", "Gym (Strength Training)", 5.0, "STRENGTH", 1),
        ExerciseCatalogEntity("hiit", "HIIT Workout", 8.0, "CARDIO", 1),
        ExerciseCatalogEntity("yoga", "Yoga", 3.0, "FLEX", 1)
    )

    override fun observeCatalog(): Flow<List<ExerciseCatalogItem>> =
        exerciseCatalogDao.observeCatalog().map { list -> list.map { it.toDomain() } }

    override suspend fun seedCatalog() {
        exerciseCatalogDao.upsertAll(seedExercises)
    }
}
