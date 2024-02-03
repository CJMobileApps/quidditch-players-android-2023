package com.cjmobileapps.quidditchplayersandroid.ui.houses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cjmobileapps.quidditchplayersandroid.R
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModelImpl.HousesState
import kotlinx.coroutines.CoroutineScope

@Composable
fun HousesUi(
    navController: NavController,
    housesViewModel: HousesViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = { },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box {
            when (val state = housesViewModel.getState()) {
                is HousesState.LoadingState -> {

                }

                is HousesState.HousesLoadedState -> {
                    HousesLoadedUi(
                        modifier = Modifier.padding(innerPadding),
                        housesViewModel = housesViewModel,
                        housesLoadedState = state,
                        navController = navController,
                    )
                }
            }
        }

        val snackbarMessage: String? = when (val state = housesViewModel.getSnackbarState()) {
            is HousesViewModelImpl.HousesSnackbarState.Idle -> null
            is HousesViewModelImpl.HousesSnackbarState.ShowGenericError -> state.error
                ?: stringResource(R.string.some_error_occurred)
        }

        if (snackbarMessage != null) {
//            DuckItListSnackbar(
//                message = snackbarMessage,
//                coroutineScope = coroutineScope,
//                snackbarHostState = snackbarHostState,
//                duckItListViewModel = duckItListViewModel
//            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousesLoadedUi(
    modifier: Modifier,
    housesViewModel: HousesViewModel,
    housesLoadedState: HousesState.HousesLoadedState,
//    duckItListLoadedState: DuckItListLoadedState,
//    duckItListViewModel: DuckItListViewModel,
    navController: NavController
) {

    val houses = housesLoadedState.houses

    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(houses) { house ->
            Card(modifier = modifier
                .fillMaxWidth()
                .clickable { }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        modifier = modifier
                            .fillMaxWidth(),
                        model = house.imageUrl,
                        contentDescription = "hghgh"
                    )

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = modifier.wrapContentWidth(),
                            textAlign = TextAlign.Center,
                            text = house.name.name
                        )

                        Text(
                            modifier = modifier
                                .wrapContentWidth()
                                .padding(start = 4.dp),
                            textAlign = TextAlign.Center,
                            text = house.emoji
                        )
                    }
                }
            }
        }
    }
    //val posts = duckItListLoadedState.posts

//    DuckItListUi(
//        modifier = modifier,
//        posts = posts,
//        lastIndex = duckItListLoadedState.posts.lastIndex,
//        onUpvoteButtonClicked = { duckItListViewModel.upvote(postId = it) },
//        onDownvoteButtonClicked = { duckItListViewModel.downvote(postId = it) }
//    )

//    when (duckItListViewModel.getDuckItListNavRouteUiState()) {
//        is DuckItListViewModelImpl.DuckItListNavRouteUi.Idle -> {}
//        is DuckItListViewModelImpl.DuckItListNavRouteUi.GoToLogInScreenUi -> {
//            navController.navigate(NavItem.LogIn.navRoute)
//            duckItListViewModel.resetNavRouteUiToIdle()
//        }
//    }
}
