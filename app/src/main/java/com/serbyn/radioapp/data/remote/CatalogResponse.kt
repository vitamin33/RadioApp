package com.serbyn.radioapp.data.remote

import com.serbyn.radioapp.domain.entity.OutlineItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatalogBodyResponse(
    @Json(name = "body")
    val body: List<CatalogItemResponse>
)

@JsonClass(generateAdapter = true)
data class OutlineBodyResponse(
    @Json(name = "body")
    val body: List<OutlineItemResponse>
)

@JsonClass(generateAdapter = true)
data class OutlineItemResponse(
    @Json(name = "element")
    val element: String,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "key")
    val key: String? = null,
    @Json(name = "text")
    val text: String,
    @Json(name = "URL")
    val url: String? = null,
    @Json(name = "guide_id")
    val guideId: String? = null,
    @Json(name = "children")
    val children: List<OutlineItemResponse>? = null,
    @Json(name = "bitrate")
    val bitrate: String? = null,
    @Json(name = "subtext")
    val subtext: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "formats")
    val formats: String?,

)

@JsonClass(generateAdapter = true)
data class CatalogItemResponse(
    @Json(name = "element")
    val element: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "text")
    val text: String,
    @Json(name = "URL")
    val url: String,
    @Json(name = "key")
    val key: String
)