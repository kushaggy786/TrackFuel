package com.trackfuel.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trackfuel.data.local.room.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE deletedAt IS NULL LIMIT 1")
    fun observeProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE deletedAt IS NULL LIMIT 1")
    suspend fun getProfile(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: UserProfileEntity)

    @Query("DELETE FROM user_profile")
    suspend fun deleteAll()
}

@Dao
interface FoodEntryDao {
    @Query("SELECT * FROM food_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL ORDER BY createdAt ASC")
    fun observeForDate(userId: String, localDate: String): Flow<List<FoodEntryEntity>>

    @Query("SELECT * FROM food_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL")
    suspend fun getForDate(userId: String, localDate: String): List<FoodEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: FoodEntryEntity)

    @Query("UPDATE food_entry SET deletedAt = :deletedAt, updatedAt = :updatedAt WHERE id = :id")
    suspend fun softDelete(id: String, deletedAt: Long = System.currentTimeMillis(), updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM food_entry")
    suspend fun deleteAll()
}

@Dao
interface WorkoutEntryDao {
    @Query("SELECT * FROM workout_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL ORDER BY createdAt ASC")
    fun observeForDate(userId: String, localDate: String): Flow<List<WorkoutEntryEntity>>

    @Query("SELECT * FROM workout_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL")
    suspend fun getForDate(userId: String, localDate: String): List<WorkoutEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: WorkoutEntryEntity)

    @Query("UPDATE workout_entry SET deletedAt = :deletedAt, updatedAt = :updatedAt WHERE id = :id")
    suspend fun softDelete(id: String, deletedAt: Long = System.currentTimeMillis(), updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM workout_entry")
    suspend fun deleteAll()
}

@Dao
interface WaterEntryDao {
    @Query("SELECT * FROM water_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL")
    fun observeForDate(userId: String, localDate: String): Flow<List<WaterEntryEntity>>

    @Query("SELECT * FROM water_entry WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL")
    suspend fun getForDate(userId: String, localDate: String): List<WaterEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: WaterEntryEntity)

    @Query("UPDATE water_entry SET deletedAt = :deletedAt, updatedAt = :updatedAt WHERE id = :id")
    suspend fun softDelete(id: String, deletedAt: Long = System.currentTimeMillis(), updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM water_entry")
    suspend fun deleteAll()
}

@Dao
interface BodyWeightLogDao {
    @Query("SELECT * FROM body_weight_log WHERE userId = :userId AND deletedAt IS NULL ORDER BY localDate DESC, createdAt DESC")
    fun observeAll(userId: String): Flow<List<BodyWeightLogEntity>>

    @Query("SELECT * FROM body_weight_log WHERE userId = :userId AND deletedAt IS NULL ORDER BY localDate DESC, createdAt DESC LIMIT 14")
    suspend fun getRecent14Days(userId: String): List<BodyWeightLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: BodyWeightLogEntity)

    @Query("UPDATE body_weight_log SET deletedAt = :deletedAt, updatedAt = :updatedAt WHERE id = :id")
    suspend fun softDelete(id: String, deletedAt: Long = System.currentTimeMillis(), updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM body_weight_log")
    suspend fun deleteAll()
}

@Dao
interface DaySummaryDao {
    @Query("SELECT * FROM day_summary WHERE userId = :userId AND localDate = :localDate LIMIT 1")
    fun observeForDate(userId: String, localDate: String): Flow<DaySummaryEntity?>

    @Query("SELECT * FROM day_summary WHERE userId = :userId AND localDate BETWEEN :fromDate AND :toDate ORDER BY localDate ASC")
    fun observeRange(userId: String, fromDate: String, toDate: String): Flow<List<DaySummaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(summary: DaySummaryEntity)

    @Query("DELETE FROM day_summary")
    suspend fun deleteAll()
}

@Dao
interface SupplementDao {
    @Query("SELECT * FROM supplement_definition WHERE userId = :userId AND deletedAt IS NULL ORDER BY createdAt ASC")
    fun observeDefinitions(userId: String): Flow<List<SupplementDefinitionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDefinition(definition: SupplementDefinitionEntity)

    @Query("UPDATE supplement_definition SET deletedAt = :deletedAt, updatedAt = :updatedAt WHERE id = :id")
    suspend fun softDeleteDefinition(id: String, deletedAt: Long = System.currentTimeMillis(), updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM supplement_log WHERE userId = :userId AND localDate = :localDate AND deletedAt IS NULL")
    fun observeLogsForDate(userId: String, localDate: String): Flow<List<SupplementLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLog(log: SupplementLogEntity)

    @Query("DELETE FROM supplement_definition")
    suspend fun deleteAllDefinitions()

    @Query("DELETE FROM supplement_log")
    suspend fun deleteAllLogs()
}

@Dao
interface ExerciseCatalogDao {
    @Query("SELECT * FROM exercise_catalog ORDER BY displayName ASC")
    fun observeCatalog(): Flow<List<ExerciseCatalogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ExerciseCatalogEntity>)
}
