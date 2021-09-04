package ru.axcheb.spotifyapi.ui.album

import kotlinx.coroutines.flow.Flow
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class AlbumUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(albumId: String): Flow<Result<Album>> = repository.album(albumId)
}