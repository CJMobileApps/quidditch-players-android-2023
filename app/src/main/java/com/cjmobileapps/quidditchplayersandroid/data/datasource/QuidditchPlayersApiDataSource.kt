package com.cjmobileapps.quidditchplayersandroid.data.datasource

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrappers
import com.cjmobileapps.quidditchplayersandroid.data.model.Status
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.withContextApiWrapper
import com.cjmobileapps.quidditchplayersandroid.util.withContextApiWrappers

class QuidditchPlayersApiDataSource(
    private val quidditchPlayersApi: QuidditchPlayersApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getAllHouses(): ResponseWrapper<List<House>> =
        withContextApiWrapper(coroutineDispatchers.io) {
            quidditchPlayersApi.getAllHouses()
        }

    suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>> =
        withContextApiWrapper(coroutineDispatchers.io) {
            quidditchPlayersApi.getPlayersByHouse(houseName)
        }

    suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>> =
        withContextApiWrappers(
            coroutineContext = coroutineDispatchers.io,
            requestFunc1 = { quidditchPlayersApi.getPlayersByHouse(houseName) },
            requestFunc2 = { quidditchPlayersApi.getPositions() },
        )

    suspend fun fetchStatusByHouseName(houseName: String): ResponseWrapper<Status> =
        withContextApiWrapper(coroutineDispatchers.io) {
            quidditchPlayersApi.getStatusByHouseName(houseName)
        }

    suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status> =
        withContextApiWrapper(coroutineDispatchers.io) {
            quidditchPlayersApi.fetchStatusByPlayerId(playerId)
        }
}
