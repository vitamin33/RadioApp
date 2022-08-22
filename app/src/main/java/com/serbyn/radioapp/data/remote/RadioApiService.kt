package com.serbyn.radioapp.data.remote

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface RadioApiService {

    @GET("?render=json")
    suspend fun getCatalog(): CatalogBodyResponse

    @GET("Browse.ashx?render=json")
    suspend fun getCatalogItemDetails(@Query("c") key: String): OutlineBodyResponse

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create(RadioApiService::class.java)
    }
}