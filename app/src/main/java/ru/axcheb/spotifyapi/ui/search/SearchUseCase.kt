package ru.axcheb.spotifyapi.ui.search

import androidx.paging.PagingSource
import ru.axcheb.spotifyapi.data.model.SearchableEntity
import ru.axcheb.spotifyapi.data.network.repository.MusicRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val repository: MusicRepository) {
    operator fun invoke(query: String): PagingSource<Int, SearchableEntity> =
        repository.search(query)
}
