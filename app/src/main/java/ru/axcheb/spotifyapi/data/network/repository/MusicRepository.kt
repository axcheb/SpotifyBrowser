package ru.axcheb.spotifyapi.data.network.repository

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.data.network.model.PlaylistsPagingSource
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val newReleasesPagingSource: NewReleasesPagingSource,
    private val playlistPagingSourceFactory: PlaylistsPagingSource.Factory
) {

    fun newReleases(): PagingSource<Int, Album> {
        return newReleasesPagingSource
    }

    fun playlists(categoryId: String): PagingSource<Int, Playlist> {
        return playlistPagingSourceFactory.create(categoryId)
    }

}