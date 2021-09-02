package ru.axcheb.spotifyapi.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.databinding.ItemNewReleasesBinding

class NewReleasesAdapter :
    PagingDataAdapter<Album, AlbumViewHolder>(AlbumDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(ItemNewReleasesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class AlbumViewHolder(private val binding: ItemNewReleasesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album?) {
        binding.icon.load(album?.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = album?.name
    }

}


private object AlbumDiffItemCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }

}