package com.cjmobileapps.quidditchplayersandroid.ui.playerdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cjmobileapps.quidditchplayersandroid.R
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.ui.QuidditchPlayersTopAppBar
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.ui.theme.QuidditchPlayersAndroid2023Theme
import com.cjmobileapps.quidditchplayersandroid.ui.util.QuidditchPlayersImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlayerDetailUi(
    navController: NavController,
    coroutineScope: CoroutineScope,
    playerDetailViewModel: PlayerDetailViewModel,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            QuidditchPlayersTopAppBar(
                navController,
                playerDetailViewModel.getTopBarTitle(),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            when (val state = playerDetailViewModel.getState()) {
                is PlayerDetailViewModelImpl.PlayerDetailState.LoadingState -> {
                }

                is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState -> {
                    PlayerDetailLoadedUi(
                        modifier =
                            Modifier
                                .padding(innerPadding),
                        state = state,
                    )
                }
            }
        }

        val snackbarMessage: String? =
            when (val state = playerDetailViewModel.getSnackbarState()) {
                is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle -> null
                is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.ShowGenericError ->
                    state.error
                        ?: stringResource(R.string.some_error_occurred)

                is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.UnableToGetPlayerError ->
                    stringResource(
                        R.string.unable_to_get_player,
                    )
            }

        if (snackbarMessage != null) {
            PlayerDetailSnackbar(
                message = snackbarMessage,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                playerDetailViewModel = playerDetailViewModel,
            )
        }
    }
}

@Composable
fun PlayerDetailSnackbar(
    message: String,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    playerDetailViewModel: PlayerDetailViewModel,
) {
    LaunchedEffect(key1 = message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
            playerDetailViewModel.resetSnackbarState()
        }
    }
}

@Composable
fun PlayerDetailLoadedUi(
    modifier: Modifier,
    state: PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState,
) {
    val player = state.player

    if (player != null) {
        PlayerDetail(modifier = modifier, player = player)
    } else {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.player_not_found),
            )
        }
    }
}

@Composable
fun PlayerDetail(
    modifier: Modifier = Modifier,
    player: PlayerState,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row {
            QuidditchPlayersImage(
                modifier =
                    Modifier
                        .size(60.dp)
                        .padding(0.dp)
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            RoundedCornerShape(4.dp),
                        ).clip(RoundedCornerShape(4.dp)),
                imageUrl = player.imageUrl,
                contentDescription = "${player.getFullName()} ${stringResource(R.string.player_image)}",
                contentScale = ContentScale.FillBounds,
            )

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = player.getFullName(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Text(
                    text = player.position,
                    color = Color.DarkGray,
                )
            }
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.house),
                color = Color.Gray,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = player.house.name,
                textAlign = TextAlign.Start,
                color = Color.Black,
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.favorite_subject),
                color = Color.Gray,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = player.favoriteSubject,
                textAlign = TextAlign.Start,
                color = Color.Black,
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.years_played),
                color = Color.Gray,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = player.yearsPlayed.toString(),
                textAlign = TextAlign.Start,
                color = Color.Black,
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val statusState = player.status.value
            val status = statusState.ifEmpty { stringResource(R.string.no_status) }

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.status),
                color = Color.Gray,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = status,
                textAlign = TextAlign.Start,
                color = Color.Black,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDetailPreview() {
    QuidditchPlayersAndroid2023Theme {
        val players = MockData.mockPlayersEntities.toPlayersState()
        PlayerDetail(
            modifier = Modifier,
            player = players.first(),
        )
    }
}
