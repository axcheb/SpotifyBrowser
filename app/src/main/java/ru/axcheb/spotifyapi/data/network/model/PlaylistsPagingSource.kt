package ru.axcheb.spotifyapi.data.network.model

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.data.network.repository.SpotifyPagingSource
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toPlaylist


class PlaylistsPagingSource @AssistedInject constructor(
    private val spotifyService: SpotifyService,
    @Assisted("categoryId") private val categoryId: String
) : SpotifyPagingSource<PlaylistResponseWrapperDto, Playlist>() {

    override suspend fun load(limit: Int, offset: Int): Response<PlaylistResponseWrapperDto> =
        spotifyService.playlists(categoryId, limit, offset)

    override fun toModel(dto: PlaylistResponseWrapperDto): List<Playlist> =
        dto.playlistResponse.items.map { it.toPlaylist() }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("categoryId") categoryId: String): PlaylistsPagingSource
    }

}