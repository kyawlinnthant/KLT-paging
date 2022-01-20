package com.example.paging3sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.paging3sample.R
import com.example.paging3sample.databinding.ItemLayoutBinding
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.Movie

class MoviePagingDataAdapter(
    private val isDark: Boolean,
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

        private val placeholder =
            if (isDark) R.drawable.placeholder_dark else R.drawable.place_holder

        fun bind(item: Movie?) = with(binding) {

            item?.let {

                this.tvTitle.text = item.title
                this.tvVote.text = item.vote_count.toString()
                Glide.with(itemView.context)
                    .load(Endpoints.IMAGE_URL + item.backdrop_path)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(placeholder)
                    .into(this.img)
            }
            itemView.setOnClickListener { onClick(absoluteAdapterPosition) }
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