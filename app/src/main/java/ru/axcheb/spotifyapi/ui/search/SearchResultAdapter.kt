package ru.axcheb.spotifyapi.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.axcheb.spotifyapi.data.model.SearchableEntity
import ru.axcheb.spotifyapi.databinding.AlbumItemBinding
import ru.axcheb.spotifyapi.databinding.ArtistItemBinding
import ru.axcheb.spotifyapi.databinding.PlaylistItemBinding
import ru.axcheb.spotifyapi.databinding.TrackItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback
import ru.axcheb.spotifyapi.ui.TrackViewHolder
import ru.axcheb.spotifyapi.ui.artist.ArtistViewHolder
import ru.axcheb.spotifyapi.ui.main.AlbumViewHolder
import ru.axcheb.spotifyapi.ui.playlists.PlaylistViewHolder

@Suppress("UNCHECKED_CAST")
class SearchResultAdapter :
    PagingDataAdapter<SearchableEntity, SearchableViewHolder<SearchableEntity>>(
        StrIdAwareDiffCallback as DiffUtil.ItemCallback<SearchableEntity>
    ) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchableViewHolder<SearchableEntity> {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            SearchableEntity.ALBUM -> AlbumViewHolder(
                AlbumItemBinding.inflate(layoutInflater, parent, false)
            ) {
                SearchFragmentDirections.actionSearchFragmentToAlbumFragment(it)
            }
            SearchableEntity.TRACK -> TrackViewHolder(
                TrackItemBinding.inflate(layoutInflater, parent, false), true
            )
            SearchableEntity.PLAYLIST -> PlaylistViewHolder(
                PlaylistItemBinding.inflate(layoutInflater, parent, false)
            ) {
                SearchFragmentDirections.actionSearchFragmentToPlaylistFragment(it)
            }
            SearchableEntity.ARTIST -> ArtistViewHolder(
                ArtistItemBinding.inflate(layoutInflater, parent, false)
            ) {
                SearchFragmentDirections.actionSearchFragmentToArtistFragment(it)
            }
            else -> throw IllegalArgumentException("Unsupported type $viewType")
        } as SearchableViewHolder<SearchableEntity>
    }

    override fun onBindViewHolder(holder: SearchableViewHolder<SearchableEntity>, position: Int) {
        holder.bind(getItem(position))
    }

}