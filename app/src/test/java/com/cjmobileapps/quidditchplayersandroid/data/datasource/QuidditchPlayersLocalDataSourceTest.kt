package com.cjmobileapps.quidditchplayersandroid.data.datasource

import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.room.QuidditchPlayersDao
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.testutil.TestCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class QuidditchPlayersLocalDataSourceTest : BaseTest() {
    private lateinit var quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource

    @Mock
    lateinit var mockQuidditchPlayersDao: QuidditchPlayersDao

    private fun setupQuidditchPlayersLocalDataSource() {
        quidditchPlayersLocalDataSource =
            QuidditchPlayersLocalDataSource(
                quidditchPlayersDao = mockQuidditchPlayersDao,
                coroutineDispatchers = TestCoroutineDispatchers,
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
            Mockito.`when`(mockQuidditchPlayersDao.getAllHouses()).thenReturn(mockGetAllHousesFlow)

            // then
            setupQuidditchPlayersLocalDataSource()
            val houses = quidditchPlayersLocalDataSource.getAllHousesFlow().first()

            // verify
            Assertions.assertEquals(
                MockData.mockHouses,
                houses,
            )
        }

    @Test
    fun `createAllHouses happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createAllHouses(MockData.mockHouses)

            Mockito.verify(mockQuidditchPlayersDao).deleteAllHouses()
            Mockito.verify(mockQuidditchPlayersDao).insertAllHouses(MockData.mockHouses)
        }

    @Test
    fun `getAllPlayersFlow happy success flow`() =
        runTest {
            // given
            val mockGetAllPlayersEntitiesFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.mockPlayersEntities)
                }
            Mockito.`when`(mockQuidditchPlayersDao.getAllPlayers()).thenReturn(mockGetAllPlayersEntitiesFlow)

            // then
            setupQuidditchPlayersLocalDataSource()
            val players = quidditchPlayersLocalDataSource.getAllPlayersFlow().first()

            // verify
            Assertions.assertEquals(
                MockData.mockPlayersEntities,
                players,
            )
        }

    @Test
    fun `createPlayersByHouseToDB happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockData.mockPlayersEntities)

            // verify
            Mockito.verify(mockQuidditchPlayersDao).deleteAllPlayers()
            Mockito.verify(mockQuidditchPlayersDao).insertAllPlayers(MockData.mockPlayersEntities)
        }
}
