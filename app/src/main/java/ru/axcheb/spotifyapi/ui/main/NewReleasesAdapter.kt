package ru.axcheb.spotifyapi.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.databinding.AlbumItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback
import ru.axcheb.spotifyapi.ui.search.SearchableViewHolder

@Suppress("UNCHECKED_CAST")
class NewReleasesAdapter :
    PagingDataAdapter<Album, AlbumViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Album>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(
            AlbumItemBinding.inflate(layoutInflater, parent, false)
        ) {
            MainFragmentDirections.actionMainFragmentToAlbumFragment(it)
        }
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class AlbumViewHolder(
    private val binding: AlbumItemBinding,
    private val directionFn: (id: String) -> NavDirections
) :
    SearchableViewHolder<Album>(binding) {

    override fun bind(album: Album?) {
        binding.icon.load(album?.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = album?.name
        album?.let {
            binding.root.setOnClickListener {
                it.findNavController().navigate(directionFn(album.id))
            }
        }
    }

}
