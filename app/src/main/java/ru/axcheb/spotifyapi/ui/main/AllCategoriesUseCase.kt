package ru.axcheb.spotifyapi.ui.main

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.repository.CategoriesRepository
import javax.inject.Inject

class AllCategoriesUseCase @Inject constructor(private val repository: CategoriesRepository) {
    operator fun invoke(): PagingSource<Int, Category> = repository.queryAll()
}