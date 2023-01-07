package com.ey.giphy.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ey.giphy.R
import com.ey.giphy.base_class.BaseFragment
import com.ey.giphy.databinding.FragmentGifListBinding
import com.ey.giphy.model.GiphyModel
import com.ey.giphy.network.Status
import com.ey.giphy.utils.Logger
import com.ey.giphy.utils.PaginationScrollListener
import com.ey.giphy.utils.Utils
import io.reactivex.disposables.CompositeDisposable

class GifListFragment : BaseFragment() {

    var TAG = "TrendingFragment"

    private var _binding: FragmentGifListBinding? = null

    private val binding get() = _binding!!
    lateinit var viewModel: GifVM
    private val compositeDisposable = CompositeDisposable()
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var gifItemAdapter: GifItemAdapter
    private val gifList = ArrayList<GiphyModel>()
    private var mIsLoading = false
    var limit = 10
    var offset = 0
    var isTrending = true
    var isSearchResult = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentGifListBinding.inflate(inflater, container, false)
        isTrending = requireArguments().getBoolean("isTrending")
        TAG = "$TAG _$isTrending"
        viewModel = ViewModelProvider(this).get(GifVM::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initViews()
        return binding.root

    }

    private fun initViews() {
        viewModel.setLayout(isTrending)
        initRecyclerView()
        if (isTrending) initSearchBar()

    }

    override fun onStart() {
        super.onStart()
        observeGifResponse()

        compositeDisposable.add(gifItemAdapter.getPublishFavourites().subscribe({
            viewModel.setFavouritesState(it)
            if (!isTrending) viewModel.getFavouritesGif()
        }, {
            Logger.e(TAG, it.message)
        }))

        compositeDisposable.add(viewModel.observeStatusChange().subscribe(
            {
                gifItemAdapter.notifyDataSetChanged()

            }, {
                Logger.e(TAG, it.message)
            })
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        gifItemAdapter.addAll(viewModel.allGifList)

    }

    override fun onResume() {
        super.onResume()

        if (isTrending) {
            if (viewModel.isSearchActivated && viewModel.isInitialSearchCall)
                viewModel.searchGifByKeyword(binding.edtSearch.text.toString(), limit, viewModel.offset)
            else if (viewModel.isInitialTrendingCall && !viewModel.isSearchActivated) viewModel.getTrendingGifs(limit, viewModel.offset)
            else {
                //refresh the already available list
                viewModel.refreshAllGifList()
            }
        } else {
            viewModel.getFavouritesGif()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.e(TAG, "inside onSaveInstanceState")

    }

    private fun initSearchBar() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.edtSearch.requestFocus()
                if (charSequence.isEmpty()) {
                    isSearchResult = false
                    viewModel.isInitialSearchCall = true
                    viewModel.isSearchActivated = false
                    viewModel.isInitialTrendingCall = true
                    viewModel.offset = 0
                    viewModel.getTrendingGifs(limit, viewModel.offset)

                } else {
                    if (!isSearchResult) if (charSequence.isNotEmpty()) {
                        viewModel.isInitialSearchCall = true
                        //re-initializing the offset to zero as we are calling the search initially
                        if (viewModel.isInitialSearchCall) viewModel.offset = 0
                        viewModel.searchGifByKeyword(charSequence.toString(), limit, viewModel.offset)
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.edtSearch.onFocusChangeListener = View.OnFocusChangeListener { p0, isFocused ->
            if (isFocused) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.edtSearch, 0)
            }
        }
    }

    private fun initRecyclerView() {
        layoutManager = GridLayoutManager(requireContext(), 2)
        gifItemAdapter = GifItemAdapter(gifList)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = gifItemAdapter
        binding.recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        binding.recyclerView.scheduleLayoutAnimation()

        if (isTrending) {
            binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                override fun loadMoreItems() {
                    if (Utils.isOnline()) {
                        mIsLoading = true
                        // offset += 5
                        //if isInitialSearch is false then call search api for pagination otherwise call trending api
                        if (viewModel.isSearchActivated) viewModel.searchGifByKeyword(binding.edtSearch.text.toString(), limit, viewModel.offset)
                        else viewModel.getTrendingGifs(limit, viewModel.offset)

                    }
                }

                override fun isLastPage(): Boolean {
                    return viewModel.offset > viewModel.totalCount
                }

                override fun isLoading(): Boolean {
                    return mIsLoading
                }
            })
        }
    }

    private fun observeGifResponse() {
        compositeDisposable.add(viewModel.getObservedGifs().subscribe({
            when (it!!.status) {

                Status.LOADING -> {
                    if (!viewModel.isSearchActivated) showNonDismissedDialog(getString(R.string.str_loading))
                }
                Status.SUCCESS -> {
                    dismissProgressDialog()
                    if (isTrending) {

                        // if initialSearchCall is true and isSearchActivated is true => clear all gifs from adapter ; reset isInitialTrending to true; set initialSearchCall to false
                        if (viewModel.isInitialSearchCall && viewModel.isSearchActivated) {
                            binding.edtSearch.hasFocus()
                            gifList.clear()
                            gifItemAdapter.notifyDataSetChanged()
                            //  gifItemAdapter.clearAllItems()
                            viewModel.isInitialTrendingCall = true
                            viewModel.isInitialSearchCall = false
                        } else if (viewModel.isInitialTrendingCall && !viewModel.isSearchActivated) {
                            gifList.clear()
                            gifItemAdapter.notifyDataSetChanged()
                            viewModel.isInitialTrendingCall = false
                        }
                        // gifList.addAll(viewModel.getNewGifList())
                        gifItemAdapter.addAll(viewModel.getNewGifList())
                        mIsLoading = false
                    } else {
                        gifList.clear()
                        gifList.addAll(viewModel.getNewGifList())
                        gifItemAdapter.notifyDataSetChanged()
                    }
                }
                Status.ERROR -> {
                    dismissProgressDialog()
                    Logger.e(TAG, it.message!!.message)
                }

            }
        }, {
            Logger.e(TAG, it.message)
        }))
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        viewModel.destroyInstances()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle) = GifListFragment().apply { arguments = bundle }
    }
}