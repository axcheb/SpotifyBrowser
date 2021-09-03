package ru.axcheb.spotifyapi.ui

import androidx.recyclerview.widget.DiffUtil
import ru.axcheb.spotifyapi.data.model.StrIdAware

object StrIdAwareDiffCallback : DiffUtil.ItemCallback<StrIdAware>() {
    override fun areItemsTheSame(oldItem: StrIdAware, newItem: StrIdAware): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StrIdAware, newItem: StrIdAware): Boolean {
        return oldItem == newItem
    }
}