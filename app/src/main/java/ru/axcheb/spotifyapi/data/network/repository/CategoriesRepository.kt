package ru.axcheb.spotifyapi.data.network.repository

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Category
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val categoriesPagingSource: CategoriesPagingSource
) {
    fun queryAll(): PagingSource<Int, Category> {
        return categoriesPagingSource
    }
}
