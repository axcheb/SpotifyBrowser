package ru.axcheb.spotifyapi.ui.main

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.SpotifyRepository
import ru.axcheb.spotifyapi.data.model.Category
import javax.inject.Inject

class AllCategoriesUseCase @Inject constructor(private val repository: SpotifyRepository) {
    operator fun invoke(): PagingSource<Int, Category> {
        return repository.queryAll()
    }
}