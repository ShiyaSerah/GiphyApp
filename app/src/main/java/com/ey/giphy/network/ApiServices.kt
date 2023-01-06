package com.ey.giphy.network

import com.ey.giphy.model.GiphyBaseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("trending")
    suspend fun getTrendingGif(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
        @Query("offset") offset: Int
        ): GiphyBaseModel
    //trending?api_key=79JpxpypaQzogciYQs3VI9YNysNxGgJN&limit=25&rating=g

    @GET("search")
    suspend fun getSearchedGif(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String,
        @Query("q") searchTerm: String,
        @Query("offset") offset: Int,
        @Query("lang") lang: String
    ): GiphyBaseModel
    //  search?api_key=79JpxpypaQzogciYQs3VI9YNysNxGgJN&q=&limit=25&offset=0&rating=g&lang=en

}