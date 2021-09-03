package ru.axcheb.spotifyapi.ui.playlists

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.databinding.PlaylistItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback

@Suppress("UNCHECKED_CAST")
class PlaylistsAdapter :
    PagingDataAdapter<Playlist, PlaylistViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Playlist>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlaylistViewHolder(private val binding: PlaylistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(playlist: Playlist?) {
        binding.icon.load(playlist?.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = playlist?.name
        binding.description.text = playlist?.description
        playlist?.let {
            binding.root.setOnClickListener {
                val direction =
                    PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistFragment(playlist.id)
                it.findNavController().navigate(direction)
            }
        }
    }
}