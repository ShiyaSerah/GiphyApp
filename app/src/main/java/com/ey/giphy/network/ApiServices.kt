package com.ey.giphy.network

import com.ey.giphy.model.GiphyBaseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("items")
    suspend fun getNewsItem(
        @Query("lineupSlug") lineupSlug: String,
        @Query("page") page: Int
    ): GiphyBaseModel
}