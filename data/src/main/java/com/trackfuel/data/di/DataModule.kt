package com.trackfuel.data.di

import android.content.Context
import androidx.room.Room
import com.trackfuel.core.common.Clock
import com.trackfuel.core.common.DateProvider
import com.trackfuel.core.common.DefaultDateProvider
import com.trackfuel.core.common.SystemClock
import com.trackfuel.data.local.room.TrackFuelDatabase
import com.trackfuel.data.local.room.dao.*
import com.trackfuel.data.repository.*
import com.trackfuel.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideClock(): Clock = SystemClock()

    @Provides
    @Singleton
    fun provideDateProvider(clock: Clock): DateProvider = DefaultDateProvider(clock)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TrackFuelDatabase =
        Room.databaseBuilder(
            context,
            TrackFuelDatabase::class.java,
            "trackfuel_db"
        ).build()

    @Provides
    fun provideUserProfileDao(db: TrackFuelDatabase): UserProfileDao = db.userProfileDao()

    @Provides
    fun provideFoodEntryDao(db: TrackFuelDatabase): FoodEntryDao = db.foodEntryDao()

    @Provides
    fun provideWorkoutEntryDao(db: TrackFuelDatabase): WorkoutEntryDao = db.workoutEntryDao()

    @Provides
    fun provideWaterEntryDao(db: TrackFuelDatabase): WaterEntryDao = db.waterEntryDao()

    @Provides
    fun provideBodyWeightLogDao(db: TrackFuelDatabase): BodyWeightLogDao = db.bodyWeightLogDao()

    @Provides
    fun provideDaySummaryDao(db: TrackFuelDatabase): DaySummaryDao = db.daySummaryDao()

    @Provides
    fun provideSupplementDao(db: TrackFuelDatabase): SupplementDao = db.supplementDao()

    @Provides
    fun provideExerciseCatalogDao(db: TrackFuelDatabase): ExerciseCatalogDao = db.exerciseCatalogDao()

    @Provides
    @Singleton
    fun provideDaySummaryRepository(
        userProfileDao: UserProfileDao,
        foodEntryDao: FoodEntryDao,
        workoutEntryDao: WorkoutEntryDao,
        waterEntryDao: WaterEntryDao,
        daySummaryDao: DaySummaryDao,
        dateProvider: DateProvider
    ): DaySummaryRepository = DaySummaryRepositoryImpl(
        userProfileDao, foodEntryDao, workoutEntryDao, waterEntryDao, daySummaryDao, dateProvider
    )

    @Provides
    @Singleton
    fun provideUserProfileRepository(
        userProfileDao: UserProfileDao,
        foodEntryDao: FoodEntryDao,
        workoutEntryDao: WorkoutEntryDao,
        waterEntryDao: WaterEntryDao,
        bodyWeightLogDao: BodyWeightLogDao,
        daySummaryDao: DaySummaryDao,
        supplementDao: SupplementDao
    ): UserProfileRepository = UserProfileRepositoryImpl(
        userProfileDao, foodEntryDao, workoutEntryDao, waterEntryDao, bodyWeightLogDao, daySummaryDao, supplementDao
    )

    @Provides
    @Singleton
    fun provideFoodEntryRepository(
        foodEntryDao: FoodEntryDao,
        daySummaryRepository: DaySummaryRepository
    ): FoodEntryRepository = FoodEntryRepositoryImpl(foodEntryDao, daySummaryRepository)

    @Provides
    @Singleton
    fun provideWorkoutRepository(
        workoutEntryDao: WorkoutEntryDao,
        daySummaryRepository: DaySummaryRepository
    ): WorkoutRepository = WorkoutRepositoryImpl(workoutEntryDao, daySummaryRepository)

    @Provides
    @Singleton
    fun provideWaterRepository(
        waterEntryDao: WaterEntryDao,
        daySummaryRepository: DaySummaryRepository
    ): WaterRepository = WaterRepositoryImpl(waterEntryDao, daySummaryRepository)

    @Provides
    @Singleton
    fun provideBodyWeightRepository(
        bodyWeightLogDao: BodyWeightLogDao,
        userProfileDao: UserProfileDao
    ): BodyWeightRepository = BodyWeightRepositoryImpl(bodyWeightLogDao, userProfileDao)

    @Provides
    @Singleton
    fun provideSupplementRepository(
        supplementDao: SupplementDao
    ): SupplementRepository = SupplementRepositoryImpl(supplementDao)

    @Provides
    @Singleton
    fun provideExerciseCatalogRepository(
        exerciseCatalogDao: ExerciseCatalogDao
    ): ExerciseCatalogRepository = ExerciseCatalogRepositoryImpl(exerciseCatalogDao)
}
