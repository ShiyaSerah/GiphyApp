package com.ey.giphy.view

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class TabTemplateAdapter(fm: Fragment?, private val titlesArr: Array<String>, private val fragsArr: Array<Fragment?>, fragFactory: Parcelable?) :
    FragmentStateAdapter(fm!!) {
    private val fragments: ArrayList<Fragment>? = null
    private val titlesList: ArrayList<String>? = null
    private val fragList: ArrayList<String>? = null
    private val fragFactory: FragmentFactory?
    override fun createFragment(position: Int): Fragment {
        return fragsArr[position]!!
    }

    override fun getItemCount(): Int {
        return fragsArr.size
    }

    init {
        this.fragFactory = fragFactory as FragmentFactory?
    }
}