package com.serbyn.radioapp.ui.station.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serbyn.radioapp.collectAsStateLifecycleAware
import com.serbyn.radioapp.ui.base.SIDE_EFFECTS_KEY
import com.serbyn.radioapp.ui.base.composable.EmptyScreen
import com.serbyn.radioapp.ui.base.composable.ErrorScreen
import com.serbyn.radioapp.ui.base.composable.LoadingScreen
import com.serbyn.radioapp.ui.main.MainContract
import com.serbyn.radioapp.ui.navigation.Navigation
import com.serbyn.radioapp.ui.navigation.navigateToStationDetails
import com.serbyn.radioapp.ui.station.StationContract
import com.serbyn.radioapp.ui.station.StationViewModel

@Composable
fun StationScreen(
    navController: NavController,
    viewModel: StationViewModel,
) {
    val state: StationContract.State by viewModel.uiState.collectAsStateLifecycleAware()

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effects.collect { effect ->
            when (effect) {
                StationContract.Effect.NavigateBack -> navController.navigate(Navigation.Routes.CATALOG)
                is StationContract.Effect.ShowToast -> TODO()
            }
        }
    }

    StationContent(navController = navController, viewModel, state)
}

@Composable
fun StationContent(
    navController: NavController,
    viewModel: StationViewModel,
    state: StationContract.State
) {
    if (state.error != null) {
        ErrorScreen(error = state.error)
    } else if (state.isLoading) {
        LoadingScreen(Color.White)
    } else if (state.stationText.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                //TODO add rendering of real state
                Text(modifier = Modifier.padding(16.dp), text = "Outline details ${state.stationText}")
                Button(onClick = {
                    viewModel.sendEvent(StationContract.Event.PressGoBack)
                }) {
                    Text(text = "Go Back")
                }
            }
        }
    } else {
        EmptyScreen()
    }
}