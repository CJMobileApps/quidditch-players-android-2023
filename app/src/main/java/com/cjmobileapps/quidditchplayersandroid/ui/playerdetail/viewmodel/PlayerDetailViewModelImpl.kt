package com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess
import com.cjmobileapps.quidditchplayersandroid.util.time.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModelImpl
    @Inject
    constructor(
        coroutineDispatchers: CoroutineDispatchers,
        savedStateHandle: SavedStateHandle,
        private val timeUtil: TimeUtil,
        private val quidditchPlayersUseCase: QuidditchPlayersUseCase,
    ) : ViewModel(),
        PlayerDetailViewModel {
        private val playerId: String = checkNotNull(savedStateHandle["playerId"])

        private val compositeJob = Job()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Timber
                    .tag(tag)
                    .e("coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message}")
                snackbarState.value = PlayerDetailSnackbarState.ShowGenericError()
            }

        private val coroutineContextHousesFlow =
            compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()
        private val playerDetailState =
            mutableStateOf<PlayerDetailState>(PlayerDetailState.LoadingState)

        private val snackbarState =
            mutableStateOf<PlayerDetailSnackbarState>(
                PlayerDetailSnackbarState.Idle,
            )

        private val tag = PlayerDetailViewModelImpl::class.java.simpleName

        override fun getState() = playerDetailState.value

        override fun getSnackbarState() = snackbarState.value

        override fun getTopBarTitle(): String {
            val state = getState()
            if (state !is PlayerDetailState.PlayerDetailLoadedState) return ""
            val player = state.player ?: return ""
            return player.getFullName()
        }

        init {
            val player = quidditchPlayersUseCase.currentPlayer.takeIf { it?.id.toString() == playerId }
            if (player != null) {
                playerDetailState.value = PlayerDetailState.PlayerDetailLoadedState(player = player)
                val state = getState() as PlayerDetailState.PlayerDetailLoadedState
                state.player?.let { playerState ->
                    getStatuesForPlayer(playerState)
                }
            } else {
                playerDetailState.value = PlayerDetailState.PlayerDetailLoadedState()
                snackbarState.value = PlayerDetailSnackbarState.UnableToGetPlayerError()
            }
        }

        private fun getStatuesForPlayer(player: PlayerState) {
            coroutineContextHousesFlow.cancelChildren()
            viewModelScope.launch(coroutineContextHousesFlow) {
                val playerId = player.id.toString()

                while (timeUtil.isDelayLoopRunning()) {
                    timeUtil.delayWithRandomTime()
                    quidditchPlayersUseCase
                        .fetchStatusByPlayerId(playerId)
                        .onSuccess { status ->
                            player.status.value = status.status
                        }.onError { _, error ->
                            Timber
                                .tag(tag)
                                .e("quidditchPlayersUseCase.fetchStatusByPlayerId(playerId) error occurred: $error \\n ${error.message}")
                        }
                }
            }
        }

        override fun resetSnackbarState() {
            snackbarState.value = PlayerDetailSnackbarState.Idle
        }

        sealed class PlayerDetailState {
            data object LoadingState : PlayerDetailState()

            data class PlayerDetailLoadedState(
                val player: PlayerState? = null,
            ) : PlayerDetailState()
        }

        sealed class PlayerDetailSnackbarState {
            data object Idle : PlayerDetailSnackbarState()

            data class ShowGenericError(
                val error: String? = null,
            ) : PlayerDetailSnackbarState()

            data class UnableToGetPlayerError(
                val error: String? = null,
            ) : PlayerDetailSnackbarState()
        }
    }
