package ru.axcheb.spotifyapi.data.network.repository

import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.network.model.NewReleasesItemsWrapperDto
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toAlbum
import javax.inject.Inject

class NewReleasesPagingSource @Inject constructor(
    private val spotifyService: SpotifyService
) : SpotifyPagingSource<NewReleasesItemsWrapperDto, Album>() {

    override suspend fun load(limit: Int, offset: Int): Response<NewReleasesItemsWrapperDto> =
        spotifyService.newReleases(limit, offset)

    override fun toModel(dto: NewReleasesItemsWrapperDto): List<Album> =
        dto.newReleasesItems.items.map { it.toAlbum() }

}