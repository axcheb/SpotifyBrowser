package ru.axcheb.spotifyapi.data.network.repository

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.SearchableEntity
import ru.axcheb.spotifyapi.data.network.model.SearchResponseDto
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toSearchableEntityList

class SearchPagingSource @AssistedInject constructor(
    private val spotifyService: SpotifyService,
    @Assisted("query") private val query: String
) : SpotifyPagingSource<SearchResponseDto, SearchableEntity>() {

    override suspend fun load(limit: Int, offset: Int): Response<SearchResponseDto> =
        spotifyService.search(query, limit, offset)

    override fun toModel(dto: SearchResponseDto): List<SearchableEntity> =
        dto.toSearchableEntityList()

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String): SearchPagingSource
    }

}