package ru.axcheb.spotifyapi.data.network.repository

import retrofit2.Response
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.model.CategoriesItemsWrapperDto
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toCategory
import javax.inject.Inject

class CategoriesPagingSource @Inject constructor(
    private val spotifyService: SpotifyService
) : SpotifyPagingSource<CategoriesItemsWrapperDto, Category>() {

    override suspend fun load(limit: Int, offset: Int): Response<CategoriesItemsWrapperDto> =
        spotifyService.categories(limit, offset)

    override fun toModel(dto: CategoriesItemsWrapperDto): List<Category> =
        dto.categories.items.map { it.toCategory() }

}