package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.Error
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapperUtil
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class QuidditchPlayersUseCase(
    private val quidditchPlayersRepository: QuidditchPlayersRepository,
) {
    private val tag = QuidditchPlayersUseCase::class.java.simpleName

    var currentPlayer: PlayerState? = null

    suspend fun getHousesFromDB(onHousesResponse: (ResponseWrapper<List<House>>) -> Unit) {
        try {
            quidditchPlayersRepository.getAllHousesFlow().collectLatest { houses ->
                onHousesResponse(
                    ResponseWrapperUtil.createResponseWrapperSuccess(houses),
                )
            }
        } catch (e: Exception) {
            Timber
                .tag(tag)
                .e("quidditchPlayersRepository.getAllHousesFlow() error occurred: $e \\n ${e.message}")
            onHousesResponse(
                ResponseWrapperUtil.createResponseWrapperError(
                    Error(isError = true, message = e.message),
                ),
            )
        }
    }

    suspend fun fetchHousesApi(): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper()

        quidditchPlayersRepository
            .getAllHouses()
            .onSuccess { houses ->
                responseWrapper =
                    try {
                        quidditchPlayersRepository.createAllHousesToDB(houses)
                        ResponseWrapperUtil.createResponseWrapperSuccess(true)
                    } catch (e: Exception) {
                        Timber
                            .tag(tag)
                            .e("quidditchPlayersRepository.getAllHouses() error occurred: $e \\n ${e.message}")
                        ResponseWrapperUtil.createResponseWrapperError(
                            Error(
                                isError = true,
                                message = e.message,
                            ),
                        )
                    }
            }.onError { statusCode, error ->
                responseWrapper =
                    ResponseWrapperUtil.createResponseWrapperError(
                        error = Error(isError = true, message = error.message),
                        statusCode = statusCode,
                    )
            }

        return responseWrapper
    }

    suspend fun fetchPlayersAndPositionsApis(houseName: String): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper()
        val responseWrappers = quidditchPlayersRepository.fetchPlayersAndPositions(houseName)
        val getPlayersByHouseResponse = responseWrappers.responseWrapper1
        val getPositionsResponse = responseWrappers.responseWrapper2

        getPositionsResponse
            .onSuccess { positions ->
                getPlayersByHouseResponse
                    .onSuccess { players ->
                        responseWrapper =
                            try {
                                quidditchPlayersRepository.createPlayersByHouseToDB(
                                    players.toPlayersEntities(positions),
                                )
                                ResponseWrapperUtil.createResponseWrapperSuccess(true)
                            } catch (e: Exception) {
                                Timber
                                    .tag(tag)
                                    .e("quidditchPlayersRepository.createPlayersByHouseToDB() error occurred: $e \\n ${e.message}")
                                ResponseWrapperUtil.createResponseWrapperError(
                                    Error(
                                        isError = true,
                                        message = e.message,
                                    ),
                                )
                            }
                    }.onError { statusCode, error ->
                        responseWrapper =
                            ResponseWrapperUtil.createResponseWrapperError(
                                error = Error(isError = true, message = error.message),
                                statusCode = statusCode,
                            )
                    }
            }.onError { statusCode, error ->
                responseWrapper =
                    ResponseWrapperUtil.createResponseWrapperError(
                        error = Error(isError = true, message = error.message),
                        statusCode = statusCode,
                    )
            }

        return responseWrapper
    }

    suspend fun getAllPlayersToDB(onPlayersResponse: (ResponseWrapper<List<PlayerEntity>>) -> Unit) {
        try {
            quidditchPlayersRepository.getAllPlayersFlow().collectLatest { players ->
                onPlayersResponse(
                    ResponseWrapperUtil.createResponseWrapperSuccess(players),
                )
            }
        } catch (e: Exception) {
            Timber
                .tag(tag)
                .e("quidditchPlayersRepository.getAllPlayersFlow() error occurred: $e \\n ${e.message}")
            onPlayersResponse(
                ResponseWrapperUtil.createResponseWrapperError(
                    Error(isError = true, message = e.message),
                ),
            )
        }
    }

    suspend fun fetchStatusByHouseName(houseName: String) = quidditchPlayersRepository.fetchStatusByHouseName(houseName)

    suspend fun fetchStatusByPlayerId(playerId: String) = quidditchPlayersRepository.fetchStatusByPlayerId(playerId)
}
