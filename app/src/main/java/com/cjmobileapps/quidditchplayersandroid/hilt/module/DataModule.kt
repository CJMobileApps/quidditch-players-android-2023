package com.cjmobileapps.quidditchplayersandroid.hilt.module

import android.content.Context
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersRepository
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersRepositoryImpl
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.room.DatabaseFactory
import com.cjmobileapps.quidditchplayersandroid.room.QuidditchPlayersDao
import com.cjmobileapps.quidditchplayersandroid.room.QuidditchPlayersDatabase
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Singleton
    @Provides
    fun quidditchPlayersApiDataSource(
        quidditchPlayersApi: QuidditchPlayersApi,
        coroutineDispatchers: CoroutineDispatchers,
    ): QuidditchPlayersApiDataSource =
        QuidditchPlayersApiDataSource(
            quidditchPlayersApi = quidditchPlayersApi,
            coroutineDispatchers = coroutineDispatchers,
        )

    @Singleton
    @Provides
    fun quidditchPlayersRepository(
        quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource,
        quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource,
    ): QuidditchPlayersRepository =
        QuidditchPlayersRepositoryImpl(
            quidditchPlayersApiDataSource = quidditchPlayersApiDataSource,
            quidditchPlayersLocalDataSource = quidditchPlayersLocalDataSource,
        )

    @Singleton
    @Provides
    fun quidditchPlayersUseCase(quidditchPlayersRepository: QuidditchPlayersRepository): QuidditchPlayersUseCase =
        QuidditchPlayersUseCase(
            quidditchPlayersRepository = quidditchPlayersRepository,
        )

    @Singleton
    @Provides
    fun quidditchPlayersDatabase(
        @ApplicationContext context: Context,
    ): QuidditchPlayersDatabase = DatabaseFactory.getDB(context)

    @Singleton
    @Provides
    fun quidditchPlayersDao(quidditchPlayersDatabase: QuidditchPlayersDatabase): QuidditchPlayersDao = quidditchPlayersDatabase.quidditchPlayersDao()

    @Singleton
    @Provides
    fun quidditchPlayersLocalDataSource(
        quidditchPlayersDao: QuidditchPlayersDao,
        coroutineDispatchers: CoroutineDispatchers,
    ): QuidditchPlayersLocalDataSource =
        QuidditchPlayersLocalDataSource(
            quidditchPlayersDao = quidditchPlayersDao,
            coroutineDispatchers = coroutineDispatchers,
        )
}
