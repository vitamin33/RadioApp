package com.serbyn.radioapp.ui.station

import com.serbyn.radioapp.ui.base.UiEffect
import com.serbyn.radioapp.ui.base.UiEvent
import com.serbyn.radioapp.ui.base.UiState
import com.serbyn.radioapp.ui.main.entity.Outline


interface StationContract {

    sealed class Event : UiEvent {
        data class LoadStation(val stationId: String) : Event()
        object PressGoBack : Event()
    }

    //UI view state
    data class State(
        val outline: Outline,
        val stationText: String,
        val error: String?,
        val isLoading: Boolean
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        object NavigateBack : Effect()
    }
}