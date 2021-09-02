package ru.axcheb.spotifyapi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.axcheb.spotifyapi.databinding.ItemErrorBinding
import ru.axcheb.spotifyapi.databinding.ItemLoadingBinding

class SpotifyLoadStateAdapter : LoadStateAdapter<SpotifyLoadStateAdapter.ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> LoadingViewHolder(
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context))
            )
            is LoadState.Error -> ErrorViewHolder(
                ItemErrorBinding.inflate(LayoutInflater.from(parent.context))
            )
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ErrorViewHolder internal constructor(private val binding: ItemErrorBinding) :
        ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }
    }

    class LoadingViewHolder internal constructor(binding: ItemLoadingBinding) :
        ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Loading)
        }
    }

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }

}