package ru.axcheb.spotifyapi.ui.playlist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.axcheb.spotifyapi.data.model.Track
import ru.axcheb.spotifyapi.databinding.TrackItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback

@Suppress("UNCHECKED_CAST")
class TracksAdapter :
    ListAdapter<Track, TrackViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Track>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TrackViewHolder(private val binding: TrackItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track?) {
        if (track != null) {
            binding.icon.load(track.albumImage) {
                placeholder(ColorDrawable(Color.TRANSPARENT))
            }
            binding.artists.text = track.artistStr()
            binding.name.text = track.name
            binding.duration.text = DateUtils.formatElapsedTime(track.durationMs / 1000L)
        }
    }

}
