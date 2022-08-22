package com.serbyn.radioapp.ui.main.entity

import androidx.compose.runtime.toMutableStateList
import com.serbyn.radioapp.domain.entity.OutlineItem
import com.serbyn.radioapp.domain.entity.CatalogItem

data class Catalog(
    val element: String,
    val type: String,
    val text: String,
    val url: String,
    val key: String,
    var detailItems: List<Outline>?,
    var error: String? = null,
    var isLoading: Boolean = false
) {
    constructor(item: CatalogItem) : this(
        item.element,
        item.type,
        item.text,
        item.url,
        item.key,
        item.detailItems?.map(::Outline)
    )

    fun toDomain(): CatalogItem {
        return CatalogItem(
            element,
            type,
            text,
            url,
            key,
            detailItems?.map { it.toDomain() }?.toMutableStateList()
        )
    }

    companion object {
        fun defaultCatalog(): Catalog {
            return Catalog("", "---", "", "", "", emptyList())
        }
    }
}

data class Outline(
    val element: String,
    val type: String?,
    val key: String?,
    val text: String,
    val url: String?,
    val guideId: String?,
    val children: List<Outline>?,
    val bitrate: String?,
    val subtext: String?,
    val image: String?,
    val formats: String?,
) {
    constructor(item: OutlineItem) : this(
        item.element,
        item.type,
        item.key,
        item.text,
        item.url,
        item.guideId,
        item.children?.map(::Outline),
        item.bitrate,
        item.subtext,
        item.image,
        item.formats
    )

    fun toDomain(): OutlineItem {
        return OutlineItem(
            element,
            type,
            key,
            text,
            url,
            guideId,
            children?.map { it.toDomain() },
            bitrate,
            subtext,
            image,
            formats
        )
    }

    companion object {
        fun defaultOutline(): Outline {
            return Outline(
                "", "", "", "", "",
                "", emptyList(), "", "", "", ""
            )
        }
    }
}
