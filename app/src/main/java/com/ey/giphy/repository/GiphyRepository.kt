package com.ey.giphy.repository

import com.ey.giphy.model.GifIdFavModel
import com.ey.giphy.model.GiphyBaseModel
import com.ey.giphy.model.GiphyModel
import com.ey.giphy.network.Resource
import java.lang.Exception

class GiphyRepository {

    private var cacheDataRepository: GiphyDataRepository? = null
    private var networkDataRepository: GiphyDataRepository? = null

    init {
        cacheDataRepository = GiphyCacheDataRepository.getInstance()
        networkDataRepository = GiphyNetworkDataRepository.getInstance()
    }

    suspend fun getTrendingGifs(limit: Int, offset: Int): Resource<GiphyBaseModel> {
        return try {
            val baseResponse = networkDataRepository!!.getTrendingGifs(limit, offset)
            if (baseResponse.meta.status == 200) {
                setFavouritesInGifList(baseResponse.data, getGifIdFromDB())
                Resource.success(baseResponse)
            } else {
                Resource.error(null, Throwable(baseResponse.meta.msg))
            }
        } catch (e: Exception) {
            Resource.error(null, Throwable(e.message))
        }
    }

    suspend fun searchForGifs(searchTerm: String, limit: Int, offset: Int): Resource<GiphyBaseModel> {
        return try {
            val baseResponse = networkDataRepository!!.searchGifByKeyword(searchTerm, limit, offset)
            if (baseResponse.meta.status == 200) {
                Resource.success(baseResponse)
            } else {
                Resource.error(null, Throwable(baseResponse.meta.msg))
            }
        } catch (e: Exception) {
            Resource.error(null, Throwable(e.message))
        }
    }

    suspend fun insertOrReplaceGifToDB(giphyModel: GiphyModel): Resource<Long> {
        return try {
            val baseResponse = cacheDataRepository!!.insertOrReplaceGifToDB(giphyModel)
            // if(null != baseResponse)
            Resource.success(baseResponse)

            // else Resource.error(null,Throwable("Failed insert in DB"))
        } catch (e: Exception) {
            Resource.error(null, Throwable(e.message))
        }
    }

    fun setFavouritesInGifList(gifList: ArrayList<GiphyModel>, favouriteList: ArrayList<String>) {
        gifList.forEach { gif ->
            gif.isFavourite = favouriteList.any { fav -> gif.id == fav }
        }

    }

    suspend fun getGifsFromDB(): Resource<ArrayList<GiphyModel>> {

        return try {
            val baseResponse = cacheDataRepository!!.getAllGifsFromDB()
            if (baseResponse.isEmpty()) {
                Resource.error(null, Throwable("No Item Available"))
            } else
                Resource.success(baseResponse)
        } catch (e: Exception) {
            Resource.error(null, Throwable(e.message))
        }

    }

    suspend fun getGifIdFromDB(): ArrayList<String> {
        return cacheDataRepository!!.getGifIdFromDB()
    }

    suspend fun getIdAndFavouriteList():ArrayList<GifIdFavModel>{
        return cacheDataRepository!!.getAllIdAndStatusFromDB()
    }

    fun destroyInstance() {

        if (null != cacheDataRepository) {
            cacheDataRepository!!.destroyInstances()
            //cacheDataRepository = null
        }
        if (null != networkDataRepository) {
            networkDataRepository!!.destroyInstances()
            //networkDataRepository = null
        }
    }
}