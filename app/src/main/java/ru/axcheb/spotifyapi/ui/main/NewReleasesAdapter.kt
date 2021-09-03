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
import ru.axcheb.spotifyapi.databinding.NewReleasesItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback

@Suppress("UNCHECKED_CAST")
class NewReleasesAdapter :
    PagingDataAdapter<Album, AlbumViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Album>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(NewReleasesItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class AlbumViewHolder(private val binding: NewReleasesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album?) {
        binding.icon.load(album?.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = album?.name
    }

}
