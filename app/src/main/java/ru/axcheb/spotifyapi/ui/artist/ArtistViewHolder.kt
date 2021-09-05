package ru.axcheb.spotifyapi.ui.artist

import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import ru.axcheb.spotifyapi.data.model.Artist
import ru.axcheb.spotifyapi.databinding.ArtistItemBinding
import ru.axcheb.spotifyapi.ui.circularPlaceholder
import ru.axcheb.spotifyapi.ui.search.SearchableViewHolder

class ArtistViewHolder(
    private val binding: ArtistItemBinding,
    private val directionFn: (id: String) -> NavDirections
) :
    SearchableViewHolder<Artist>(binding) {
    override fun bind(item: Artist?) {
        binding.name.text = item?.name
        binding.type.text = item?.type
        binding.icon.load(item?.iconUrl) {
            placeholder(binding.icon.circularPlaceholder())
            transformations(CircleCropTransformation())
        }

        item?.let { artist ->
            binding.root.setOnClickListener {
                it.findNavController().navigate(directionFn(artist.id))
            }
        }
    }
}