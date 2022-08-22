package com.serbyn.radioapp.data.mapper

import com.serbyn.radioapp.PairMapper
import com.serbyn.radioapp.data.remote.CatalogItemResponse
import com.serbyn.radioapp.domain.entity.OutlineItem
import com.serbyn.radioapp.domain.entity.CatalogItem
import javax.inject.Inject

open class CatalogResponseToDomainMapper @Inject constructor() :
    PairMapper<CatalogItemResponse, List<OutlineItem>?, CatalogItem> {
    override fun invoke(
        responseItem: CatalogItemResponse,
        details: List<OutlineItem>?
    ): CatalogItem {
        return CatalogItem(
            responseItem.element,
            responseItem.type,
            responseItem.text,
            responseItem.url,
            responseItem.key,
            details
        )
    }
}