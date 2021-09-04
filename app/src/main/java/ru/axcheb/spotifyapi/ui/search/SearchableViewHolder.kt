package ru.axcheb.spotifyapi.ui.search

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.axcheb.spotifyapi.data.model.SearchableEntity

abstract class SearchableViewHolder<T : SearchableEntity>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T?)
}