package ru.axcheb.spotifyapi.ui

import androidx.recyclerview.widget.DiffUtil
import ru.axcheb.spotifyapi.data.model.StrIdAware

class StrIdAwareDiffCallback<T : StrIdAware> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}