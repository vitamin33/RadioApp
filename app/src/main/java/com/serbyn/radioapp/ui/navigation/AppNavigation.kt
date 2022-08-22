package com.serbyn.radioapp.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.serbyn.radioapp.ui.station.StationViewModel
import com.serbyn.radioapp.ui.station.composable.StationScreen
import com.serbyn.radioapp.ui.main.composable.MainScreen
import com.serbyn.radioapp.ui.navigation.Navigation.Args.STATION_ID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Navigation.Routes.CATALOG) {
        composable(route = Navigation.Routes.CATALOG) { MainScreen(navController) }
        composable(
            route = Navigation.Routes.STATIONS,
            arguments = listOf(navArgument(name = STATION_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val stationId =
                requireNotNull(backStackEntry.arguments?.getString(STATION_ID)) { "Station id is required as an argument" }

            val stationViewModel = hiltViewModel<StationViewModel>(backStackEntry)
            StationScreen(navController, stationViewModel)
        }
    }
}

object Navigation {

    object Args {
        const val STATION_ID = "station_id"
    }

    object Routes {
        const val CATALOG = "catalog"
        const val STATIONS = "$CATALOG/{$STATION_ID}"
    }
}

fun NavController.navigateToStationDetails(stationId: String?) {
    navigate(route = "${Navigation.Routes.CATALOG}/$stationId")
}