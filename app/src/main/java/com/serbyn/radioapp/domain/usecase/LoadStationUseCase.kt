package com.serbyn.radioapp.domain.usecase

import com.serbyn.radioapp.domain.CatalogRepository
import com.serbyn.radioapp.domain.entity.StationItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class LoadStationUseCase @Inject constructor(
    private val currenciesRepository: CatalogRepository
) {

    suspend operator fun invoke(stationId: String?): Flow<StationItem> {

        return currenciesRepository.getStationDetails(stationId)
    }
}