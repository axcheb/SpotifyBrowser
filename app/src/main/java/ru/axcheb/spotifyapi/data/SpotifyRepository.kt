package ru.axcheb.spotifyapi.data

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.CategoriesPagingSource
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val categoriesPagingSource: CategoriesPagingSource
) {
    fun queryAll(): PagingSource<Int, Category> {
        return categoriesPagingSource
    }
}
