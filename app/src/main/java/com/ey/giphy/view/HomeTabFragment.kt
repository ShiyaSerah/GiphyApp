package com.ey.giphy.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ey.giphy.GiphyApplication
import com.ey.giphy.R
import com.ey.giphy.base_class.BaseFragment
import com.ey.giphy.databinding.FragmentTabTemplateBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeTabFragment : BaseFragment() {
    var binding: FragmentTabTemplateBinding? = null
    private var titlesArr: Array<String>? = null
    private var fragmentsList: Array<String>? = null
    internal var fragmentsArr: Array<Fragment?>? = null
    private var adapter: TabTemplateAdapter? = null
    private var fragment: Fragment? = null
    var publishTabChange: PublishSubject<Fragment> = PublishSubject.create()
    val currentPage = 0


    companion object {

        const val FRAGMENT_LIST = "fragment_list"
        const val FRAGMENT_TITLE = "fragment_title"
        const val BUNDLE = "bundle"
        fun getInstance(titleList: Array<String>, fragmentList: Array<String>, bundle: Bundle): HomeTabFragment {
            val args = Bundle()
            args.putStringArray(FRAGMENT_TITLE, titleList)
            args.putStringArray(FRAGMENT_LIST, fragmentList)
            args.putBundle(BUNDLE, bundle)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_template, container, false)

        init()
        setClickListener()
        return binding!!.root
    }

    private fun setClickListener() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        titlesArr =
            arrayOf(GiphyApplication.getContext().getString(R.string.str_trending), GiphyApplication.getContext().getString(R.string.str_favourites))
        fragmentsList = arrayOf(FragmentFactory.TRENDING_FRAGMENT, FragmentFactory.TRENDING_FRAGMENT)

        val fragFactory = FragmentFactory()


        fragmentsArr = arrayOfNulls(fragmentsList!!.size)

        for (i in fragmentsList!!.indices) {
            val fragmentName = fragmentsList!![i]
            fragmentsArr!![i] = fragFactory.create(fragmentName, getBundles(i))
        }

        if (fragmentsList!!.size > 3) {
            binding!!.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        } else {
            binding!!.tabLayout.tabMode = TabLayout.MODE_FIXED
        }

        adapter = TabTemplateAdapter(this, titlesArr!!, fragmentsArr!!, fragFactory)//code related to viewpager2

        binding!!.viewPager.adapter = adapter

        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            tab.text = titlesArr?.get(position)
        }.attach()

        val currentPage = 0
        binding!!.viewPager.currentItem = currentPage

        binding!!.viewPager.offscreenPageLimit = titlesArr!!.size
        binding!!.viewPager.offscreenPageLimit = 1
        fragment = if (currentPage < titlesArr!!.size) {
            fragmentsArr!![currentPage]
        } else {
            fragmentsArr!![0] // for first time when Activity gets opened
        }

        publishTabChange.onNext(fragment!!)
        binding!!.viewPager.registerOnPageChangeCallback(viewPagerPageChangeCallback)
        binding!!.viewPager.isUserInputEnabled = true

    }

    private var viewPagerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        var pos = currentPage
        override fun onPageSelected(position: Int) {
            pos = position
            fragment = fragmentsArr!!.get(position)
            publishTabChange.onNext(fragment!!)
        }

        override fun onPageScrollStateChanged(state: Int) {
            fragment = fragmentsArr!![pos]
        }
    }

    fun getPublishTabChange(): Observable<Fragment> {
        return publishTabChange.hide()
    }

    private fun getBundles(position: Int): Bundle {
        val bundle = Bundle()
        if (position == 0) {
            bundle.putBoolean("isTrending", true)
        } else {
            bundle.putBoolean("isTrending", false)
        }
        return bundle
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (fragment != null) {
            fragment!!.onActivityResult(requestCode, resultCode, data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun changeTab(position: Int) {
        binding!!.viewPager.currentItem = position
    }


    override fun onStop() {
        super.onStop()

    }
}