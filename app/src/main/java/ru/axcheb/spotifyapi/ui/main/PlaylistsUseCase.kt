package ru.axcheb.spotifyapi.ui.main

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class PlaylistsUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(categoryId: String): PagingSource<Int, Playlist> =
        repository.playlists(categoryId)

}