@file:OptIn(ExperimentalMaterialApi::class)

package com.serbyn.radioapp.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import com.serbyn.radioapp.ui.base.UiEffect
import com.serbyn.radioapp.ui.base.UiEvent
import com.serbyn.radioapp.ui.base.UiState
import com.serbyn.radioapp.ui.main.entity.Catalog
import com.serbyn.radioapp.ui.main.entity.Outline

interface MainContract {

    sealed class Event : UiEvent {
        data class LoadCatalog(val tabIndex: Int? = null) : Event()
        data class StationChosen(val station: Outline) : Event()
    }

    //UI view state
    data class State(
        val catalogList: List<Catalog>,
        val error: String?,
        val isLoading: Boolean,
        val selectedTabIndex: Int?,
        val initChange: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        data class NavigateToStationDetails(val stationId: String?) : Effect()
    }
}