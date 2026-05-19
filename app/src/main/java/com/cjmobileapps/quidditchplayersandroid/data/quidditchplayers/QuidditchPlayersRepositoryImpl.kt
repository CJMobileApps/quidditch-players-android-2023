package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity

class QuidditchPlayersRepositoryImpl(
    private val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource,
    private val quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource,
) : QuidditchPlayersRepository {
    override suspend fun getAllHouses() = quidditchPlayersApiDataSource.getAllHouses()

    override suspend fun getAllHousesFlow() = quidditchPlayersLocalDataSource.getAllHousesFlow()

    override suspend fun createAllHousesToDB(houses: List<House>) = quidditchPlayersLocalDataSource.createAllHouses(houses)

    override suspend fun getPlayersByHouse(houseName: String) = quidditchPlayersApiDataSource.getPlayersByHouse(houseName)

    override suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) = quidditchPlayersLocalDataSource.createPlayersByHouseToDB(players)

    override suspend fun getAllPlayersFlow() = quidditchPlayersLocalDataSource.getAllPlayersFlow()

    override suspend fun fetchPlayersAndPositions(houseName: String) = quidditchPlayersApiDataSource.fetchPlayersAndPositions(houseName)

    override suspend fun fetchStatusByHouseName(houseName: String) = quidditchPlayersApiDataSource.fetchStatusByHouseName(houseName)

    override suspend fun fetchStatusByPlayerId(playerId: String) = quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId)
}
