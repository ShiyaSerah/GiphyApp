package com.ey.giphy.repository

import com.ey.giphy.constants.Constants
import com.ey.giphy.model.GiphyBaseModel
import com.ey.giphy.model.GiphyModel
import com.ey.giphy.network.ApiServiceGenerator

class GiphyNetworkDataRepository : GiphyDataRepository {

    private var apiServices = ApiServiceGenerator.getGiphyService()

    companion object {

        private var providerInstance: GiphyNetworkDataRepository? = null

        @Synchronized
        fun getInstance(): GiphyNetworkDataRepository {
            if (providerInstance == null) {
                providerInstance = GiphyNetworkDataRepository()
            }
            return providerInstance!!
        }
    }

    override suspend fun getTrendingGifs(limit: Int, offset: Int): GiphyBaseModel {
        return apiServices.getTrendingGif(Constants.API_KEY, limit, "g", offset)
    }

    override suspend fun searchGifByKeyword(searchTerm: String, limit: Int, offset: Int): GiphyBaseModel {
        return apiServices.getSearchedGif(Constants.API_KEY, limit, "g", searchTerm, offset, "en")
    }

    override suspend fun getFavouriteGifsFromDB(): ArrayList<GiphyModel> {
        TODO("Not yet implemented")    }

    override suspend fun insertGifToDB(giphyModel: GiphyModel): Long {
        TODO("Not yet implemented")    }

    override suspend fun getGifIdFromDB(): ArrayList<String> {
        TODO("Not yet implemented")    }

    override suspend fun getAllGifsFromDB(): ArrayList<GiphyModel> {
        TODO("Not yet implemented")
    }

    override fun destroyInstances() {
        if (null != providerInstance) providerInstance = null
    }
}