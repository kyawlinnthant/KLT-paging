package com.example.paging3sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.paging3sample.R
import com.example.paging3sample.databinding.ItemLayoutBinding
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.Movie

class MoviePagingDataAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {

    fun getClickItem(position: Int): Movie? = getItem(position)

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    inner class MovieViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) = with(binding) {

            item?.let {
                this.textView.text = item.title
                Glide.with(itemView.context)
                    .load(Endpoints.IMAGE_URL + item.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(this.imageView)
            }
            itemView.setOnClickListener { onClick }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}