package com.serbyn.radioapp.ui.station

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import com.serbyn.radioapp.domain.usecase.LoadStationUseCase
import com.serbyn.radioapp.ui.base.BaseViewModel
import com.serbyn.radioapp.ui.main.entity.Outline
import com.serbyn.radioapp.ui.navigation.Navigation.Args.STATION_ID
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StationViewModel @Inject constructor(
    private val loadStationUseCase: LoadStationUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<StationContract.Event, StationContract.State, StationContract.Effect>() {

    init {
        val stationId = savedStateHandle.get<String>(STATION_ID).orEmpty()
        sendEvent(StationContract.Event.LoadStation(stationId))
    }

    override fun handleEvent(event: StationContract.Event) {
        when (event) {
            is StationContract.Event.LoadStation -> {
                viewModelScope.launch {
                    loadStationUseCase(event.stationId).collect {
                        setState {
                            copy(stationText = "Loaded station id: ${event.stationId}")
                        }
                    }
                }
            }
            StationContract.Event.PressGoBack -> sendEffect(StationContract.Effect.NavigateBack)
        }
    }

    override fun initState() = StationContract.State(
        Outline.defaultOutline(),
        "empty",
        error = null,
        isLoading = false
    )
}