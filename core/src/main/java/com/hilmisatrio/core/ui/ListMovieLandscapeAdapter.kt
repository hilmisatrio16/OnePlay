package com.hilmisatrio.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilmisatrio.core.databinding.ItemListFilmLandscapeBinding
import com.hilmisatrio.core.domain.model.Movie

class ListMovieLandscapeAdapter(var onClickItem: ((Movie) -> Unit)? = null) :
    RecyclerView.Adapter<ListMovieLandscapeAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallbackUser)

    fun submitData(valueList: ArrayList<Movie>) {
        differ.submitList(valueList)
    }

    class ViewHolder(var binding: ItemListFilmLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemListFilmLandscapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataMovie = differ.currentList[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(dataMovie.posterPath)
                .into(imageFilm)
            tvTitleFilm.text = dataMovie.title
            tvOverview.text = dataMovie.overview
            tvRateFilm.text = String.format("%.1f", dataMovie.voteAverage).toDouble().toString()
        }

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(dataMovie)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}