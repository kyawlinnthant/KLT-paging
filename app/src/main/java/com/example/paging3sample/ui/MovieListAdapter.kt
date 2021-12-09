package com.example.paging3sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.paging3sample.databinding.ItemLayoutBinding
import com.example.paging3sample.model.Movie

class MovieListAdapter(
    private val onClick : (Int) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(newsDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieListViewHolder).bind(getItem(position))
    }

    companion object {
        val newsDiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getClickItem(position: Int): Movie = getItem(position)

    inner class MovieListViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) {

            item?.let {
                with(item) {
                    binding.textView.text = this.title
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/original/" + this.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.imageView)
                }
            }

            itemView.setOnClickListener { onClick(adapterPosition) }
        }
    }
}