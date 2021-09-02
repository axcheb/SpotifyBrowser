package ru.axcheb.spotifyapi.data.network.repository

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Album
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val newReleasesPagingSource: NewReleasesPagingSource
) {

    fun queryAll(): PagingSource<Int, Album> {
        return newReleasesPagingSource
    }

}