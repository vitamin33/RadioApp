package com.serbyn.radioapp.data.mapper

import com.serbyn.radioapp.Mapper
import com.serbyn.radioapp.data.remote.OutlineItemResponse
import com.serbyn.radioapp.domain.entity.OutlineItem
import javax.inject.Inject

open class OutlineToDomainMapper @Inject constructor() : Mapper<OutlineItemResponse, OutlineItem> {
    override fun invoke(responseItem: OutlineItemResponse): OutlineItem {
        return OutlineItem(
            responseItem.element,
            responseItem.type,
            responseItem.key,
            responseItem.text,
            responseItem.url,
            responseItem.guideId,
            responseItem.children?.map(this),
            responseItem.bitrate,
            responseItem.subtext,
            responseItem.image,
            responseItem.formats
        )
    }
}