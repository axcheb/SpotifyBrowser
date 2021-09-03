package ru.axcheb.spotifyapi.ui.playlist

import kotlinx.coroutines.flow.Flow
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class PlaylistUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(playlistId: String): Flow<Result<Playlist>> =
        repository.playlist(playlistId)
}