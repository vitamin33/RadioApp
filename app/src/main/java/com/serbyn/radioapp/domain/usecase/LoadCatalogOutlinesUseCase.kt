package com.serbyn.radioapp.domain.usecase

import com.serbyn.radioapp.domain.CatalogRepository
import com.serbyn.radioapp.domain.entity.CatalogItem
import kotlinx.coroutines.flow.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

open class LoadCatalogOutlinesUseCase @Inject constructor(
    private val currenciesRepository: CatalogRepository
) {

    suspend operator fun invoke(tabIndex: Int?, list: List<CatalogItem>): Flow<List<CatalogItem>> {
        val resultKey = if (tabIndex != null) {
            parseKeyQueryValue(list[tabIndex].url)
        } else {
            null
        }

        return currenciesRepository.getCatalog(resultKey)
    }

    private fun parseKeyQueryValue(url: String): String? {

        //usually better to move such constant parameters(as "c") in one place
        // and declare as constants
        return url.toHttpUrlOrNull()?.queryParameter("c")
    }
}