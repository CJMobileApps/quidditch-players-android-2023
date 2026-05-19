package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.times

class QuidditchPlayersUseCaseTest : BaseTest() {
    private lateinit var quidditchPlayersUseCase: QuidditchPlayersUseCase

    @Mock
    private lateinit var mockQuidditchPlayersRepository: QuidditchPlayersRepository

    private fun setupQuidditchPlayersUseCase() {
        quidditchPlayersUseCase =
            QuidditchPlayersUseCase(
                quidditchPlayersRepository = mockQuidditchPlayersRepository,
            )
    }

    @Test
    fun `getHousesFromDB() happy success flow`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockData.mockHouses)
                }

            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllHousesFlow()).thenReturn(mockGetAllHousesFlow)

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                Assertions.assertEquals(
                    MockData.mockHousesResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun `getHousesFromDB() throw exception`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllHousesFlow()).thenReturn(mockGetAllHousesFlow)

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                Assertions.assertEquals(
                    MockData.mockHousesGenericErrorResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun `fetchHousesApi() happy success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllHouses()).thenReturn(MockData.mockHousesResponseWrapper)

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            Mockito.verify(mockQuidditchPlayersRepository).createAllHousesToDB(MockData.mockHouses)
            Assertions.assertEquals(
                MockData.mockTrueResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchHousesApi() then response error at getAllHouses() error flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllHouses()).thenReturn(MockData.mockHousesGenericErrorResponseWrapper)

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            Mockito.verify(mockQuidditchPlayersRepository, times(0)).createAllHousesToDB(MockData.mockHouses)
            Assertions.assertEquals(
                MockData.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchHousesApi() throw exception at createAllHousesToDB error flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllHouses()).thenReturn(MockData.mockHousesResponseWrapper)
            given(mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses)).willAnswer {
                throw Exception("Some error")
            }

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            Assertions.assertEquals(
                MockData.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis() happy success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockRavenclawPlayersAndPositionsResponseWrappers)

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            Mockito.verify(mockQuidditchPlayersRepository).createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
            Assertions.assertEquals(
                MockData.mockTrueResponseWrapper,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis() with players error response error flow`() =
        runTest {
            // when
            Mockito
                .`when`(mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name))
                .thenReturn(MockData.mockRavenclawPlayersErrorAndPositionsResponseWrappers)

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            Mockito.verify(mockQuidditchPlayersRepository, times(0)).createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
            Assertions.assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis() with positions error response error flow`() =
        runTest {
            // when
            Mockito
                .`when`(mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name))
                .thenReturn(MockData.mockRavenclawPlayersAndPositionsErrorResponseWrappers)

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            Mockito.verify(mockQuidditchPlayersRepository, times(0)).createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
            Assertions.assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis() throw exception at createAllHousesToDB error flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockRavenclawPlayersAndPositionsResponseWrappers)
            given(mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))).willAnswer {
                throw Exception("Some error")
            }

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            Mockito.verify(mockQuidditchPlayersRepository, times(1)).createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
            Assertions.assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `getAllPlayersToDB() happy success flow`() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
                }

            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllPlayersFlow()).thenReturn(mockGetAllPlayersPlayerEntityFlow)

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                Assertions.assertEquals(
                    MockData.mockRavenclawPlayersEntitiesResponseWrapper,
                    players,
                )
            }
        }

    @Test
    fun `getAllPlayersToDB() throw getAllPlayersFlow() error flow`() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            Mockito.`when`(mockQuidditchPlayersRepository.getAllPlayersFlow()).thenReturn(mockGetAllPlayersPlayerEntityFlow)

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                Assertions.assertEquals(
                    MockData.mockRavenclawPlayersEntitiesResponseWrapperError,
                    players,
                )
            }
        }

    @Test
    fun `fetchStatusByHouseName() happy success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersRepository.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `fetchStatusByPlayerId() happy success flow`() =
        runTest {
            // given
            val playerId =
                MockData
                    .ravenclawTeam()
                    .first()
                    .id
                    .toString()

            // when
            Mockito.`when`(mockQuidditchPlayersRepository.fetchStatusByPlayerId(playerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByPlayerId(playerId)

            // verify
            Assertions.assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `set currentPlayer`() {
        val playerState =
            MockData
                .ravenclawTeam()
                .first()
                .toPlayerEntity(MockData.mockPositions)
                .toPlayerState()

        // then
        setupQuidditchPlayersUseCase()
        quidditchPlayersUseCase.currentPlayer = playerState

        // verify
        Assertions.assertEquals(
            playerState,
            quidditchPlayersUseCase.currentPlayer,
        )
    }
}
