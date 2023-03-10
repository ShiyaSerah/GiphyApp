package com.ey.giphy.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ey.giphy.model.GifIdFavModel
import com.ey.giphy.model.GiphyModel

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(giphyModel: GiphyModel): Long

    @Query("SELECT id FROM GiphyModel WHERE isFavourite = 1")
    suspend fun getAllFavouriteId(): MutableList<String>

    @Query("SELECT * FROM GiphyModel WHERE isFavourite = 1")
    suspend fun getAllGifs(): MutableList<GiphyModel>

    @Query("SELECT id, isFavourite FROM GiphyModel")
    suspend fun getAllIdAndFavStatus(): MutableList<GifIdFavModel>
}