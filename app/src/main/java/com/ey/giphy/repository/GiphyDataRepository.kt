package com.ey.giphy.repository

import com.ey.giphy.model.GiphyBaseModel
import com.ey.giphy.model.GiphyModel

interface GiphyDataRepository {

    suspend fun getTrendingGifs(limit: Int, offset: Int): GiphyBaseModel

    suspend fun searchGifByKeyword(searchTerm: String, limit: Int, offset: Int): GiphyBaseModel

    suspend fun getFavouriteGifsFromDB(): ArrayList<GiphyModel>

    suspend fun insertGifToDB(giphyModel: GiphyModel): Long

    suspend fun getGifIdFromDB():ArrayList<String>

    suspend fun getAllGifsFromDB():ArrayList<GiphyModel>

    fun destroyInstances()
}