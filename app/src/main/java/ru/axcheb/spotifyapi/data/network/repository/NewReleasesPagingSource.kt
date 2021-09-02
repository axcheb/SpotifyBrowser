package ru.axcheb.spotifyapi.data.network.repository

import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.network.model.NewReleasesResponseWrapperDto
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toAlbum
import javax.inject.Inject

class NewReleasesPagingSource @Inject constructor(
    private val spotifyService: SpotifyService
) : SpotifyPagingSource<NewReleasesResponseWrapperDto, Album>() {

    override suspend fun load(limit: Int, offset: Int): Response<NewReleasesResponseWrapperDto> =
        spotifyService.newReleases(limit, offset)

    override fun toModel(dto: NewReleasesResponseWrapperDto): List<Album> =
        dto.newReleasesResponse.items.map { it.toAlbum() }

}