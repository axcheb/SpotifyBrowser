package ru.axcheb.spotifyapi.ui.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import coil.load
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.databinding.PlaylistItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback
import ru.axcheb.spotifyapi.ui.circularPlaceholder
import ru.axcheb.spotifyapi.ui.search.SearchableViewHolder

class PlaylistsAdapter :
    PagingDataAdapter<Playlist, PlaylistViewHolder>(StrIdAwareDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistItemBinding.inflate(layoutInflater, parent, false)) {
            PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistFragment(it)
        }
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlaylistViewHolder(
    private val binding: PlaylistItemBinding,
    private val directionFn: (id: String) -> NavDirections
) :
    SearchableViewHolder<Playlist>(binding) {

    override fun bind(playlist: Playlist?) {
        binding.icon.load(playlist?.iconUrl) {
            placeholder(binding.icon.circularPlaceholder())
        }
        binding.name.text = playlist?.name
        binding.description.text = playlist?.description
        if (playlist?.description.isNullOrBlank()) {
            binding.description.visibility = View.GONE
        }

        playlist?.let {
            binding.root.setOnClickListener {
                it.findNavController().navigate(directionFn(playlist.id))
            }
        }
    }
}