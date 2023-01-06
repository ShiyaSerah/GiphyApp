package com.ey.giphy.view

import com.ey.giphy.utils.Logger.d
import android.os.Parcelable
import android.os.Parcel
import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentFactory() : Parcelable {


    constructor(parcel: Parcel) : this() {
    }

    fun create(fragmentId: String?, bundle: Bundle?): Fragment {
        d(TAG, "In fragfactory")
        var frag = Fragment()
        when (fragmentId) {
            TRENDING_FRAGMENT -> {
                frag = GifListFragment.newInstance(bundle!!)
            }
           /* FAVOURITES_FRAGMENT -> {
                frag = FavouritesFragment.newInstance(bundle!!)

            }*/
        }
        return frag
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FragmentFactory> {
        const val TRENDING_FRAGMENT = "trendingFragment"
        const val FAVOURITES_FRAGMENT = "favouritesFragment"

        private val TAG = FragmentFactory::class.java.simpleName
        override fun createFromParcel(parcel: Parcel): FragmentFactory {
            return FragmentFactory(parcel)
        }

        override fun newArray(size: Int): Array<FragmentFactory?> {
            return arrayOfNulls(size)
        }
    }
}