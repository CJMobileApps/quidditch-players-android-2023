package com.cjmobileapps.quidditchplayersandroid.data.datasource

import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.testutil.TestCoroutineDispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class QuidditchPlayersApiDataSourceTest : BaseTest() {
    private lateinit var quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource

    @Mock
    lateinit var mockQuidditchPlayersApi: QuidditchPlayersApi

    private fun setupQuidditchPlayersApiDataSource() {
        quidditchPlayersApiDataSource =
            QuidditchPlayersApiDataSource(
                quidditchPlayersApi = mockQuidditchPlayersApi,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `houses happy success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersApi.getAllHouses()).thenReturn(MockData.mockHousesDeferredResponseSuccess)

            // then
            setupQuidditchPlayersApiDataSource()
            val houses = quidditchPlayersApiDataSource.getAllHouses()

            // verify
            Assertions.assertEquals(
                MockData.mockHousesResponseWrapper,
                houses,
            )
        }

    @Test
    fun `get players by house success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name))
                .thenReturn(
                    MockData.mockRavenclawPlayersDeferredResponseSuccess,
                )

            // then
            setupQuidditchPlayersApiDataSource()
            val players = quidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun`fetch players and positions success flow`() =
        runTest {
            // when
            Mockito.`when`(mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name))
                .thenReturn(
                    MockData.mockRavenclawPlayersDeferredResponseSuccess,
                )
            Mockito.`when`(mockQuidditchPlayersApi.getPositions()).thenReturn(MockData.mockPositionsDeferredResponseSuccess)

            // then
            setupQuidditchPlayersApiDataSource()
            val playersAndPositions = quidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockRavenclawPlayersAndPositionsResponseWrappers,
                playersAndPositions,
            )
        }

    @Test
    fun `fetch status by house name success flow`() =
        runTest {
            // when
            Mockito.`when`(
                mockQuidditchPlayersApi.getStatusByHouseName(houseName = HouseName.RAVENCLAW.name),
            ).thenReturn(MockData.mockStatusDeferredResponseSuccess)

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.fetchStatusByHouseName(houseName = HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `fetch status by player id success flow`() =
        runTest {
            // given
            val playerId = MockData.ravenclawTeam().first().id.toString()

            // when
            Mockito.`when`(
                mockQuidditchPlayersApi.fetchStatusByPlayerId(playerId = playerId),
            ).thenReturn(MockData.mockStatusDeferredResponseSuccess)

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId = playerId)

            // verify
            Assertions.assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }
}
