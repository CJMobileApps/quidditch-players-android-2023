package com.cjmobileapps.quidditchplayersandroid.ui.playerslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cjmobileapps.quidditchplayersandroid.R
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.ui.QuidditchPlayersTopAppBar
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.PlayerDetail
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.ui.theme.QuidditchPlayersAndroid2023Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlayersListUi(
    navController: NavController,
    playersListViewModel: PlayersListViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            QuidditchPlayersTopAppBar(
                navController,
                playersListViewModel.getTopBarTitle(),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box {
            when (val state = playersListViewModel.getState()) {
                is PlayersListViewModelImpl.PlayersListState.LoadingState -> {
                    PlayerListShimmerLoadingUi(modifier = Modifier.padding(innerPadding))
                }

                is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState -> {
                    PlayersListLoadedUi(
                        modifier = Modifier.padding(innerPadding),
                        playersListViewModel = playersListViewModel,
                        playersLoadedState = state,
                        navController = navController,
                    )
                }
            }
        }

        val snackbarMessage: String? =
            when (val state = playersListViewModel.getSnackbarState()) {
                is PlayersListViewModelImpl.PlayersListSnackbarState.Idle -> null
                is PlayersListViewModelImpl.PlayersListSnackbarState.ShowGenericError ->
                    state.error
                        ?: stringResource(R.string.some_error_occurred)

                is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError ->
                    stringResource(
                        R.string.unable_to_get_players,
                    )
            }

        if (snackbarMessage != null) {
            PlayerListSnackbar(
                message = snackbarMessage,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                playersListViewModel = playersListViewModel,
            )
        }
    }
}

@Composable
fun PlayerListSnackbar(
    message: String,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    playersListViewModel: PlayersListViewModel,
) {
    LaunchedEffect(key1 = message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
            playersListViewModel.resetSnackbarState()
        }
    }
}

@Composable
fun PlayersListLoadedUi(
    modifier: Modifier,
    playersListViewModel: PlayersListViewModel,
    playersLoadedState: PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState,
    navController: NavController,
) {
    val players = playersLoadedState.players

    PlayerListUi(
        modifier = modifier,
        players = players,
    ) { player ->
        playersListViewModel.goToPlayerDetailUi(player)
    }

    when (val navigateRouteUiValue = playersListViewModel.getPlayersListNavRouteUiState()) {
        is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle -> {}
        is PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi -> {
            navController.navigate(navigateRouteUiValue.getNavRouteWithArguments())
            playersListViewModel.resetNavRouteUiToIdle()
        }
    }
}

@Composable
fun PlayerListUi(
    modifier: Modifier,
    players: List<PlayerState>,
    onCardClick: (player: PlayerState) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
    ) {
        items(players) { player ->
            PlayerCardUi(player, onCardClick = onCardClick)
        }
    }
}

// TODO this preview not working
@Preview(showBackground = true)
@Composable
fun PlayerListUiPreview() {
    QuidditchPlayersAndroid2023Theme {
        val players = MockData.mockRavenclawPlayersEntities.toPlayersState()
        PlayerListUi(modifier = Modifier, players = players) {
        }
    }
}

@Composable
fun PlayerCardUi(
    player: PlayerState,
    onCardClick: (player: PlayerState) -> Unit,
) {
    ElevatedCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable { onCardClick.invoke(player) },
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        PlayerDetail(player = player)
    }
}

// TODO this preview not working
@Preview(showBackground = true)
@Composable
fun PlayerCardUiPreview() {
    QuidditchPlayersAndroid2023Theme {
        val players = MockData.mockRavenclawPlayersEntities.toPlayersState()
        PlayerCardUi(player = players.first()) {
        }
    }
}
