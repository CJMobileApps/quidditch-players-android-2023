package com.cjmobileapps.quidditchplayersandroid.ui.playerdetail

import androidx.lifecycle.SavedStateHandle
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.TestTimeUtil
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.given

class PlayerDetailViewModelImplTest : BaseTest() {
    private lateinit var playerDetailViewModel: PlayerDetailViewModel

    @Mock
    private lateinit var mockSavedStateHandle: SavedStateHandle

    @Mock
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val testTimeUtil = TestTimeUtil

    private fun setupPlayerDetailViewModel() {
        testTimeUtil.resetTestTimeUtil()
        playerDetailViewModel =
            PlayerDetailViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                savedStateHandle = mockSavedStateHandle,
                timeUtil = testTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch player happy flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.currentPlayer).thenReturn(mockRavenPlayer)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            Assertions.assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            Assertions.assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            Assertions.assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            Assertions.assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            Assertions.assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            Assertions.assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `fetch player then return status error flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.currentPlayer).thenReturn(mockRavenPlayer)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapperGenericError)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            Assertions.assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            Assertions.assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            Assertions.assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            Assertions.assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            Assertions.assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            Assertions.assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `fetch player then throw status error flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.currentPlayer).thenReturn(mockRavenPlayer)
            given(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).willAnswer {
                throw Exception("Some error")
            }

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            // this assertion doesn't work not sure why
            Assertions.assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.ShowGenericError)
            Assertions.assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            Assertions.assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            Assertions.assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            Assertions.assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            Assertions.assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            Assertions.assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            Assertions.assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `throw fetching player is null flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.currentPlayer).thenReturn(null)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            var snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            Assertions.assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.UnableToGetPlayerError)

            // then
            playerDetailViewModel.resetSnackbarState()
            snackbarState = playerDetailViewModel.getSnackbarState()
            Assertions.assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
        }
}
