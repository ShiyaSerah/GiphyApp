package com.ey.giphy.repository

import androidx.room.*
import com.ey.giphy.GiphyApplication
import com.ey.giphy.model.GiphyModel
import com.ey.giphy.model.ImageProperties
import com.ey.giphy.model.Images
import com.ey.giphy.repository.dao.GiphyDao


@Database(entities = [GiphyModel::class, Images::class,ImageProperties::class], version = 1, exportSchema = false)
abstract class RoomDatabaseHelper : RoomDatabase() {


    abstract fun giphyDao(): GiphyDao


    companion object {
        private var INSTANCE: RoomDatabaseHelper? = null



        fun getInstance(): RoomDatabaseHelper? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(GiphyApplication.getContext(), RoomDatabaseHelper::class.java, "giphy.db")
                    .fallbackToDestructiveMigration()
                    .build()

                synchronized(RoomDatabase::class) {
                }

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}