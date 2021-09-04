package ru.axcheb.spotifyapi.ui.playlists

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.databinding.PlaylistItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback
import ru.axcheb.spotifyapi.ui.search.SearchableViewHolder

@Suppress("UNCHECKED_CAST")
class PlaylistsAdapter :
    PagingDataAdapter<Playlist, PlaylistViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Playlist>) {

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
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = playlist?.name
        binding.description.text = playlist?.description
        playlist?.let {
            binding.root.setOnClickListener {
                it.findNavController().navigate(directionFn(playlist.id))
            }
        }
    }
}