package ru.axcheb.spotifyapi.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.axcheb.spotifyapi.data.model.Artist
import ru.axcheb.spotifyapi.databinding.RelatedArtistItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback

class RelatedArtistAdapter :
    ListAdapter<Artist, RelatedArtistViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Artist>) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RelatedArtistViewHolder(
            RelatedArtistItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        ) {
            ArtistFragmentDirections.actionArtistFragmentSelf(it)
        }
    }

    override fun onBindViewHolder(holder: RelatedArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
