package com.serbyn.radioapp.data

import com.serbyn.radioapp.data.mapper.OutlineToDomainMapper
import com.serbyn.radioapp.data.mapper.CatalogResponseToDomainMapper
import com.serbyn.radioapp.data.remote.RadioApiService
import com.serbyn.radioapp.domain.CatalogRepository
import com.serbyn.radioapp.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.radioapp.domain.entity.CatalogItem
import com.serbyn.radioapp.domain.entity.StationItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val remoteRadioApi: RadioApiService,
    private val responseToDomainMapper: CatalogResponseToDomainMapper,
    private val responseDetailsToDomainMapper: OutlineToDomainMapper
) : CatalogRepository {

    //TODO add logic for local database caching with Room
    private var catalog: List<CatalogItem>? = null

    override suspend fun getCatalog(key: String?): Flow<List<CatalogItem>> {
        return flow {
            emit(getRemoteCatalogList(key))
        }
    }

    override suspend fun getStationDetails(stationId: String?): Flow<StationItem> {
        return flow {
            //TODO replace to real data
            emit(StationItem(stationId ?: "test id", "test url"))
        }
    }

    private suspend fun getRemoteCatalogList(key: String?): List<CatalogItem> {
        return withContext(dispatchers.io) {
            if (catalog == null) {
                catalog = remoteRadioApi.getCatalog().body.map {
                    responseToDomainMapper(it, null)
                }
            }
            val resultKey = key ?: "local"

            if (!isCatalogDetailsCached(resultKey)) {
                getRemoteCatalogDetails(resultKey)
            }

            Timber.d("After filtering: $catalog")
            catalog!!
        }
    }

    private fun isCatalogDetailsCached(key: String): Boolean {
        return catalog?.find { it.key == key }?.detailItems != null
    }

    private suspend fun getRemoteCatalogDetails(key: String) {
        val detailItems =
            remoteRadioApi.getCatalogItemDetails(key).body.map(
                responseDetailsToDomainMapper
            )
        catalog?.find { it.key == key }?.detailItems = detailItems
    }
}