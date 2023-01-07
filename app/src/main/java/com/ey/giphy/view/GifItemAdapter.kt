package com.ey.giphy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ey.giphy.GiphyApplication
import com.ey.giphy.R
import com.ey.giphy.databinding.ItemGifBinding
import com.ey.giphy.model.GiphyModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GifItemAdapter(var gifItems: ArrayList<GiphyModel>) : RecyclerView.Adapter<GifItemAdapter.ViewHolder?>() {

    private lateinit var binding: ItemGifBinding
    private val publishFavouriteChecked: PublishSubject<GiphyModel> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(GiphyApplication.getContext())
            .load(gifItems[holder.absoluteAdapterPosition].images!!.imageProperties!!.url)
            .thumbnail(Glide.with(GiphyApplication.getContext()).load(R.drawable.loading))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .into(holder.binding.imgGif)


        holder.binding.checkFavourites.isChecked = gifItems[holder.absoluteAdapterPosition].isFavourite
        holder.binding.checkFavourites.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                gifItems[holder.absoluteAdapterPosition].isFavourite = isChecked
                publishFavouriteChecked.onNext(gifItems[holder.absoluteAdapterPosition])

            }
        })

    }

    override fun getItemCount(): Int {
        return gifItems.size
    }

    fun addAll(gifList: ArrayList<GiphyModel>) {
        for (gif in gifList) {
            add(gif)
        }
    }

    fun getPublishFavourites(): Observable<GiphyModel> {
        return publishFavouriteChecked.hide()
    }

    private fun add(gif: GiphyModel) {
        gifItems.add(gif)
        notifyItemInserted(gifItems.size - 1)
    }

    inner class ViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

}