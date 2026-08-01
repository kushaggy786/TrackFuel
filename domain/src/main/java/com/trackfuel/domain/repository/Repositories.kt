package com.trackfuel.domain.repository

import com.trackfuel.domain.model.*
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun observeProfile(): Flow<UserProfile?>
    suspend fun getProfile(): UserProfile?
    suspend fun upsert(profile: UserProfile)
    suspend fun deleteAllUserData()
}

interface FoodEntryRepository {
    fun observeForDate(userId: String, localDate: String): Flow<List<FoodEntry>>
    suspend fun upsert(entry: FoodEntry)
    suspend fun softDelete(id: String)
}

interface WorkoutRepository {
    fun observeForDate(userId: String, localDate: String): Flow<List<WorkoutEntry>>
    suspend fun upsert(entry: WorkoutEntry)
    suspend fun softDelete(id: String)
}

interface WaterRepository {
    fun observeTotalMl(userId: String, localDate: String): Flow<Int>
    suspend fun add(entry: WaterEntry)
    suspend fun softDelete(id: String)
}

interface BodyWeightRepository {
    fun observeAll(userId: String): Flow<List<BodyWeightLog>>
    suspend fun getRecent14Days(userId: String): List<BodyWeightLog>
    suspend fun add(entry: BodyWeightLog)
    suspend fun softDelete(id: String)
}

interface DaySummaryRepository {
    fun observeForDate(userId: String, localDate: String): Flow<DaySummary?>
    fun observeRange(userId: String, fromDate: String, toDate: String): Flow<List<DaySummary>>
    suspend fun recompute(userId: String, localDate: String)
}

interface SupplementRepository {
    fun observeDefinitions(userId: String): Flow<List<SupplementDefinition>>
    suspend fun upsertDefinition(definition: SupplementDefinition)
    suspend fun softDeleteDefinition(id: String)
    fun observeLogsForDate(userId: String, localDate: String): Flow<List<SupplementLog>>
    suspend fun upsertLog(log: SupplementLog)
}

interface ExerciseCatalogRepository {
    fun observeCatalog(): Flow<List<ExerciseCatalogItem>>
    suspend fun seedCatalog()
}
