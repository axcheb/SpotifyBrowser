package ru.axcheb.spotifyapi.ui.main

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class NewReleasesUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(): PagingSource<Int, Album> = repository.newReleases()
}