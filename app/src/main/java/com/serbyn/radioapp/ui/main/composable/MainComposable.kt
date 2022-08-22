package com.serbyn.radioapp.ui.main.composable

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serbyn.radioapp.collectAsStateLifecycleAware
import com.serbyn.radioapp.ui.base.SIDE_EFFECTS_KEY
import com.serbyn.radioapp.ui.base.composable.EmptyScreen
import com.serbyn.radioapp.ui.base.composable.ErrorScreen
import com.serbyn.radioapp.ui.base.composable.LoadingScreen
import com.serbyn.radioapp.ui.main.MainViewModel
import com.serbyn.radioapp.ui.main.MainContract
import com.serbyn.radioapp.ui.navigation.Navigation
import com.serbyn.radioapp.ui.navigation.navigateToStationDetails
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state: MainContract.State by viewModel.uiState.collectAsStateLifecycleAware()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MainContract.Effect.ShowToast -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is MainContract.Effect.NavigateToStationDetails -> {
                    navController.navigateToStationDetails(effect.stationId)
                }
            }
        }
    }

    MainContent(navController = navController, viewModel, state)
}

@Composable
fun MainContent(
    navController: NavController,
    viewModel: MainViewModel,
    state: MainContract.State
) {
    if (state.error != null) {
        ErrorScreen(error = state.error)
    } else if (state.isLoading) {
        LoadingScreen(MaterialTheme.colorScheme.primary)
    } else if (state.catalogList.isNotEmpty()) {
        HistoryContent(navController, viewModel, state)
    } else {
        EmptyScreen()
    }
}

@Composable
fun HistoryContent(
    navController: NavController,
    viewModel: MainViewModel,
    state: MainContract.State
) {
    CategoryTabBar(
        state,
        { chosenStation ->
            viewModel.sendEvent(MainContract.Event.StationChosen(chosenStation))
        },
        { tabIndex ->
            viewModel.sendEvent(MainContract.Event.LoadCatalog(tabIndex))
        }
    )
}