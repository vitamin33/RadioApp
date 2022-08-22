package com.serbyn.radioapp.domain.entity

data class CatalogItem(
    val element: String,
    val type: String,
    val text: String,
    val url: String,
    val key: String,
    var detailItems: List<OutlineItem>?
)

data class OutlineItem(
    val element: String,
    val type: String?,
    val key: String?,
    val text: String,
    val url: String?,
    val guideId: String?,
    val children: List<OutlineItem>?,
    val bitrate: String?,
    val subtext: String?,
    val image: String?,
    val formats: String?,
)