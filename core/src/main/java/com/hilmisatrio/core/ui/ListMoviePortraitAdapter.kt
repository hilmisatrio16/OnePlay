package com.hilmisatrio.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilmisatrio.core.databinding.ItemListFilmPotraitBinding
import com.hilmisatrio.core.domain.model.Movie

class ListMoviePortraitAdapter(var onClickItem: ((Movie) -> Unit)? = null) :
    RecyclerView.Adapter<ListMoviePortraitAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemListFilmPotraitBinding) :
        RecyclerView.ViewHolder(binding.root)

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            ItemListFilmPotraitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataMovie = differ.currentList[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(dataMovie.posterPath)
                .into(imageFilm)
            tvTitleFilm.text = dataMovie.title
        }

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(dataMovie)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}