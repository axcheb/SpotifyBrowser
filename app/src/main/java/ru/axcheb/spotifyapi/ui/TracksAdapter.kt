package ru.axcheb.spotifyapi.ui

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import ru.axcheb.spotifyapi.data.model.Track
import ru.axcheb.spotifyapi.databinding.TrackItemBinding
import ru.axcheb.spotifyapi.ui.search.SearchableViewHolder

@Suppress("UNCHECKED_CAST")
class TracksAdapter(private val showIcon: Boolean = true) :
    ListAdapter<Track, TrackViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Track>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackItemBinding.inflate(layoutInflater, parent, false), showIcon)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TrackViewHolder(private val binding: TrackItemBinding, private val showIcon: Boolean) :
    SearchableViewHolder<Track>(binding) {

    override fun bind(track: Track?) {
        if (track != null) {
            if (!showIcon) {
                binding.icon.visibility = View.GONE
            }

            binding.icon.load(track.albumImage) {
                placeholder(binding.icon.circularPlaceholder())
            }
            binding.artists.text = track.artistStr()
            binding.name.text = track.name
            binding.duration.text = DateUtils.formatElapsedTime(track.durationMs / 1000L)
        }
    }

}
