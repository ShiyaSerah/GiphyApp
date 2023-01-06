package com.ey.giphy.repository

import com.ey.giphy.model.GiphyBaseModel
import com.ey.giphy.model.GiphyModel

class GiphyCacheDataRepository : GiphyDataRepository {

    private var roomDatabase = RoomDatabaseHelper.getInstance()

    companion object {

        private var providerInstance: GiphyCacheDataRepository? = null

        @Synchronized
        fun getInstance(): GiphyCacheDataRepository {
            if (providerInstance == null) {
                providerInstance = GiphyCacheDataRepository()
            }
            return providerInstance!!
        }
    }


    override suspend fun getTrendingGifs(limit: Int, offset: Int): GiphyBaseModel {
        TODO("Not yet implemented")
    }

    override suspend fun searchGifByKeyword(searchTerm: String, limit: Int, offset: Int): GiphyBaseModel {
        TODO("Not yet implemented")
    }

    override suspend fun getFavouriteGifsFromDB(): ArrayList<GiphyModel> {
        return roomDatabase!!.giphyDao().getAllGifs() as ArrayList<GiphyModel>
    }

    override suspend fun insertGifToDB(giphyModel: GiphyModel): Long {
        return roomDatabase!!.giphyDao().insertGif(giphyModel)
    }

    override suspend fun getGifIdFromDB(): ArrayList<String> {
        return roomDatabase!!.giphyDao().getAllId() as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
    }

    override suspend fun getAllGifsFromDB(): ArrayList<GiphyModel> {
        return roomDatabase!!.giphyDao().getAllGifs() as ArrayList<GiphyModel> /* = java.util.ArrayList<com.ey.giphy.model.GiphyModel> */
    }

    override fun destroyInstances() {
        if (null != providerInstance) providerInstance = null
    }
}