package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class QuidditchPlayersRepositoryImplTest : BaseTest() {
    private lateinit var quidditchPlayersRepositoryImpl: QuidditchPlayersRepositoryImpl

    @Mock
    private lateinit var mockQuidditchPlayersApiDataSource: QuidditchPlayersApiDataSource

    @Mock
    private lateinit var mockQuidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource

    private fun setupQuidditchPlayersRepositoryImpl() {
        quidditchPlayersRepositoryImpl =
            QuidditchPlayersRepositoryImpl(
                quidditchPlayersApiDataSource = mockQuidditchPlayersApiDataSource,
                quidditchPlayersLocalDataSource = mockQuidditchPlayersLocalDataSource,
            )
    }

    @Test
    fun `getAllHouses happy success flow`() =
        runTest {
            // Given
            Mockito.`when`(mockQuidditchPlayersApiDataSource.getAllHouses()).thenReturn(MockData.mockHousesResponseWrapper)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHouses()

            // verify
            Assertions.assertEquals(
                MockData.mockHousesResponseWrapper,
                houses,
            )
        }

    @Test
    fun `getAllHousesFlow happy success flow`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockData.mockHouses)
                }
            Mockito.`when`(mockQuidditchPlayersLocalDataSource.getAllHousesFlow()).thenReturn(mockGetAllHousesFlow)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHousesFlow().first()

            // verify
            Assertions.assertEquals(
                MockData.mockHouses,
                houses,
            )
        }

    @Test
    fun `createPlayersByHouseToDB happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersRepositoryImpl()
            quidditchPlayersRepositoryImpl.createPlayersByHouseToDB(MockData.mockPlayersEntities)

            // verify
            Mockito.verify(mockQuidditchPlayersLocalDataSource).createPlayersByHouseToDB(MockData.mockPlayersEntities)
        }

    @Test
    fun `getPlayersByHouse happy success flow`() =
        runTest {
            // given
            Mockito.`when`(mockQuidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockRavenclawPlayersResponseWrapper)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun `createAllHousesToDB() happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersRepositoryImpl()
            quidditchPlayersRepositoryImpl.createAllHousesToDB(MockData.mockHouses)

            // verify
            Mockito.verify(mockQuidditchPlayersLocalDataSource).createAllHouses(MockData.mockHouses)
        }

    @Test
    fun `getAllPlayersFlow() happy success flow`() =
        runTest {
            val mockGetAllPlayersEntitiesFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.mockPlayersEntities)
                }
            Mockito.`when`(mockQuidditchPlayersLocalDataSource.getAllPlayersFlow()).thenReturn(mockGetAllPlayersEntitiesFlow)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getAllPlayersFlow().first()

            // verify
            Assertions.assertEquals(
                MockData.mockPlayersEntities,
                players,
            )
        }

    @Test
    fun `fetchPlayersAndPositions() happy success flow`() =
        runTest {
            // given
            Mockito.`when`(mockQuidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockRavenclawPlayersAndPositionsResponseWrappers)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val playersAndPositions = quidditchPlayersRepositoryImpl.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockRavenclawPlayersAndPositionsResponseWrappers,
                playersAndPositions,
            )
        }

    @Test
    fun `fetchStatusByHouseName() happy success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersApiDataSource.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status = quidditchPlayersRepositoryImpl.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

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
            Mockito.`when`(mockQuidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status = quidditchPlayersRepositoryImpl.fetchStatusByPlayerId(playerId = playerId)

            // verify
            Assertions.assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }
}
