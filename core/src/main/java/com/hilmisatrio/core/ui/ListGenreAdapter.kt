package com.hilmisatrio.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hilmisatrio.core.databinding.ItemListGenreBinding
import com.hilmisatrio.core.domain.model.GenreMovie

class ListGenreAdapter : RecyclerView.Adapter<ListGenreAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<GenreMovie>() {
        override fun areItemsTheSame(
            oldItem: GenreMovie,
            newItem: GenreMovie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenreMovie,
            newItem: GenreMovie
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallbackUser)

    fun submitData(valueList: ArrayList<GenreMovie>) {
        differ.submitList(valueList)
    }

    class ViewHolder(var binding: ItemListGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataGenre = differ.currentList[position]

        holder.binding.genre.text= dataGenre.name
    }
}