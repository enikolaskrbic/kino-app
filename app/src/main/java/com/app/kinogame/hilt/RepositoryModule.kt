package com.app.kinogame.hilt

import android.app.Application
import androidx.room.Room
import com.app.kinogame.data.repository.GameResultRepository
import com.app.kinogame.data.repository.GameResultRepositoryImpl
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.repository.KinoGameRepositoryImpl
import com.app.kinogame.data.repository.TalonRepository
import com.app.kinogame.data.repository.TalonRepositoryImpl
import com.app.kinogame.data.repository.locale.GameResultsLocalDataSource
import com.app.kinogame.data.repository.locale.KinoGameLocalDataSource
import com.app.kinogame.data.repository.locale.TalonLocalDataSource
import com.app.kinogame.data.repository.remote.KinoGameRemoteDataSource
import com.app.kinogame.data.room.KinoDatabase
import com.app.kinogame.data.room.dao.KinoGameDao
import com.app.kinogame.data.room.dao.ResultsGameDao
import com.app.kinogame.data.room.dao.TalonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): KinoDatabase {
        return Room.databaseBuilder(application, KinoDatabase::class.java, "kino.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideKinoGameDao(db: KinoDatabase): KinoGameDao {
        return db.kinoGameDao()
    }

    @Singleton
    @Provides
    fun provideTalonDao(db: KinoDatabase): TalonDao {
        return db.talonDao()
    }

    @Singleton
    @Provides
    fun provideResultDao(db: KinoDatabase): ResultsGameDao {
        return db.resultDao()
    }

    @Provides
    @Singleton
    fun provideTalonRepository(
        talonLocalDataSource: TalonLocalDataSource,
    ): TalonRepository {
        return TalonRepositoryImpl(talonLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideGameRepository(
        gameLocalDataSource: KinoGameLocalDataSource,
        gameRemoteDataSource: KinoGameRemoteDataSource
    ): KinoGameRepository {
        return KinoGameRepositoryImpl(gameLocalDataSource, gameRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGameResultRepository(
        gameResultsLocalDataSource: GameResultsLocalDataSource,
    ): GameResultRepository {
        return GameResultRepositoryImpl(gameResultsLocalDataSource)
    }
}
