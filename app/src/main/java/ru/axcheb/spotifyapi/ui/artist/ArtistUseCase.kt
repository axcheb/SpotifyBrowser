package ru.axcheb.spotifyapi.ui.artist

import kotlinx.coroutines.flow.Flow
import ru.axcheb.spotifyapi.data.model.Artist
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class ArtistUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(artistId: String): Flow<Result<Artist>> = repository.artist(artistId)
}