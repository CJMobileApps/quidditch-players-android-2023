package com.cjmobileapps.quidditchplayersandroid.data.datasource

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.room.QuidditchPlayersDao
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QuidditchPlayersLocalDataSource(
    private val quidditchPlayersDao: QuidditchPlayersDao,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getAllHousesFlow(): Flow<List<House>> =
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.getAllHouses()
        }

    suspend fun createAllHouses(houses: List<House>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllHouses()
            quidditchPlayersDao.insertAllHouses(houses)
        }
    }

    suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> =
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.getAllPlayers()
        }

    suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllPlayers()
            quidditchPlayersDao.insertAllPlayers(players)
        }
    }
}
