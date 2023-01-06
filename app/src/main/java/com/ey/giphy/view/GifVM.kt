package com.ey.giphy.view

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.ey.giphy.GiphyApplication
import com.ey.giphy.base_class.BaseViewModel
import com.ey.giphy.model.GiphyBaseModel
import com.ey.giphy.model.GiphyModel
import com.ey.giphy.network.Resource
import com.ey.giphy.network.Status
import com.ey.giphy.repository.GiphyRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GifVM : BaseViewModel(GiphyApplication.getInstance()) {

    private val repository = GiphyRepository()
    private var observeGifs: PublishSubject<Resource<ArrayList<GiphyModel>>> = PublishSubject.create()
    private var observeInsert: PublishSubject<Resource<Long>> = PublishSubject.create()
    var isInitialTrendingCall = true
    var isInitialSearchCall = true
    var allGifList = ArrayList<GiphyModel>()
    var gifList = ArrayList<GiphyModel>()
    var totalCount = 0
    var offset = 0
    var searchLayoutVisibility = ObservableField(View.VISIBLE)
    var noFavTextVisibility = ObservableField(View.GONE)
    var recyclerVisibility = ObservableField(View.VISIBLE)
    var isTrending = true
    var isSearchActivated = false
    val newItemCount = 10

    fun setLayout(isTrending: Boolean) {
        this.isTrending = isTrending
        if (isTrending) setTrendingLayout()
        else setFavouriteLayout()
    }

    private fun setTrendingLayout() {
        searchLayoutVisibility.set(View.VISIBLE)
        recyclerVisibility.set(View.VISIBLE)
        noFavTextVisibility.set(View.GONE)
    }

    private fun setFavouriteLayout() {
        searchLayoutVisibility.set(View.GONE)
        noFavTextVisibility.set(View.GONE)
        recyclerVisibility.set(View.GONE)

    }

    fun getTrendingGifs(limit: Int, offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            observeGifs.onNext(Resource.loading(null))
        }
        val baseResponse = repository.getTrendingGifs(limit, offset)
        withContext(Dispatchers.Main) {
            observeGifs.onNext(formatResponse(baseResponse))
        }

    }

    fun searchGifByKeyword(searchTerm: String, limit: Int, offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        isSearchActivated = true
        withContext(Dispatchers.Main) {
            observeGifs.onNext(Resource.loading(null))
        }

        val baseResponse = repository.searchForGifs(searchTerm, limit, offset)
        if (isInitialSearchCall) allGifList.clear()


        withContext(Dispatchers.Main) {
            observeGifs.onNext(formatResponse(baseResponse))
        }


    }

    private fun formatResponse(baseResponse: Resource<GiphyBaseModel>): Resource<ArrayList<GiphyModel>> {
        return if (Status.SUCCESS == baseResponse.status) {
            recyclerVisibility.set(View.VISIBLE)
            val giphyBase = baseResponse.data!!
            totalCount = giphyBase.pagination.totalCount
            if (totalCount > offset) this@GifVM.offset += newItemCount
            allGifList.addAll(giphyBase.data)
            gifList.clear()
            gifList.addAll(baseResponse.data.data)
            Resource.success(baseResponse.data.data)
        } else {
            Resource.error(null, baseResponse.message!!)
        }

    }

    fun setFavouritesState(giphyModel: GiphyModel) = viewModelScope.launch(Dispatchers.IO) {
        refreshAllGifList(giphyModel)
        withContext(Dispatchers.Main) {
            observeInsert.onNext(Resource.loading(null))
        }

        val baseResponse = repository.insertGifToDB(giphyModel)

        withContext(Dispatchers.Main) {
            observeInsert.onNext(baseResponse)
        }
    }

    fun refreshAllGifList(gifModel: GiphyModel) {
        gifList.forEach { gif ->
            if (gifModel.id == gif.id)
                gif.isFavourite = gifModel.isFavourite
        }
    }

    fun getFavouritesGif() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            observeGifs.onNext(Resource.loading(null))
        }
        val baseResponse = repository.getGifsFromDB()
        val resource = if (Status.SUCCESS == baseResponse.status) {
            noFavTextVisibility.set(View.GONE)
            recyclerVisibility.set(View.VISIBLE)
            isInitialTrendingCall = false
            val giphyBase = baseResponse.data!!

            allGifList.addAll(giphyBase)
            gifList.clear()
            gifList.addAll(baseResponse.data)
            Resource.success(baseResponse.data)
        } else {
            if (baseResponse.message!!.message == "No Item Available") {
                noFavTextVisibility.set(View.VISIBLE)
                recyclerVisibility.set(View.GONE)
            }

            Resource.error(null, baseResponse.message)
        }
        withContext(Dispatchers.Main) {
            observeGifs.onNext(resource)
        }
    }

    fun getNewGifList(): ArrayList<GiphyModel> {
        return gifList
    }

    fun getObservedGifs(): Observable<Resource<ArrayList<GiphyModel>>> {
        return observeGifs.hide()
    }

    override fun destroyInstances() {
        repository.destroyInstance()
    }
}