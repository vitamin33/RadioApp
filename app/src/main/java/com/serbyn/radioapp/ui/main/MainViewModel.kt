package com.serbyn.radioapp.ui.main

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.serbyn.radioapp.domain.usecase.*
import com.serbyn.radioapp.ui.base.BaseViewModel
import com.serbyn.radioapp.ui.main.MainContract.*
import com.serbyn.radioapp.ui.main.entity.Catalog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadCatalogUseCase: LoadCatalogOutlinesUseCase
) : BaseViewModel<Event, State, Effect>() {

    init {
        sendEvent(Event.LoadCatalog())
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.LoadCatalog -> {
                viewModelScope.launch {
                    val tabIndex = event.tabIndex
                    loadCatalogUseCase(
                        event.tabIndex,
                        uiState.value.catalogList.map { it.toDomain() }
                    )
                        .onStart {
                            if (tabIndex == null) {
                                setState { copy(isLoading = true) }
                            } else {
                                val list =
                                    copyCatalogList(uiState.value.catalogList, tabIndex, true)
                                setState { copy(catalogList = list, initChange = true) }
                            }
                        }
                        .map { it.map(::Catalog) }
                        .catch {
                            setState { copy(error = "Error while loading catalog from API:  ${it.message}") }
                        }
                        .collect {
                            if (tabIndex == null) {
                                setState { copy(catalogList = it, isLoading = false) }
                            } else {
                                val list = copyCatalogList(it, tabIndex, false)
                                setState { copy(catalogList = list, isLoading = false, initChange = false) }
                            }
                        }
                }
            }
            is Event.StationChosen -> {
                //TODO move this logic inside some use case for parameter parsing
                val stationId = event.station.url?.toHttpUrlOrNull()?.queryParameter("id")

                sendEffect(Effect.NavigateToStationDetails(stationId))
            }
        }
    }

    private fun copyCatalogList(
        catalogList: List<Catalog>,
        keyIndex: Int,
        isLoading: Boolean
    ): List<Catalog> {
        val list = catalogList.toMutableStateList()
        list[keyIndex].isLoading = isLoading
        return list
    }

    override fun initState() = State(
        emptyList(),
        error = null,
        isLoading = false,
        selectedTabIndex = null
    )
}