package com.serbyn.radioapp.domain

import com.serbyn.radioapp.domain.entity.CatalogItem
import com.serbyn.radioapp.domain.entity.StationItem
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    suspend fun getCatalog(key: String? = null) : Flow<List<CatalogItem>>

    suspend fun getStationDetails(stationId: String? = null) : Flow<StationItem>

}