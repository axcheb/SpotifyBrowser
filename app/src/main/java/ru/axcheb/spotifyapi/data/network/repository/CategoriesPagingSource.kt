package ru.axcheb.spotifyapi.data.network.repository

import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.model.CategoriesResponseWrapperDto
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toCategory
import javax.inject.Inject

class CategoriesPagingSource @Inject constructor(
    private val spotifyService: SpotifyService
) : SpotifyPagingSource<CategoriesResponseWrapperDto, Category>() {

    override suspend fun load(limit: Int, offset: Int): Response<CategoriesResponseWrapperDto> =
        spotifyService.categories(limit, offset)

    override fun toModel(dto: CategoriesResponseWrapperDto): List<Category> =
        dto.categories.items.map { it.toCategory() }

}