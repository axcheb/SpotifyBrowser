package ru.axcheb.spotifyapi.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.*
import ru.axcheb.spotifyapi.data.model.SearchableEntity
import javax.inject.Inject

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val searchResults: StateFlow<PagingData<SearchableEntity>> = query
        .map(::newPager)
        .flatMapLatest { it.flow }.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, SearchableEntity> {
        return Pager(PagingConfig(3, enablePlaceholders = false)) {
            newPagingSource?.invalidate()
            searchUseCase(query).also { newPagingSource = it }
        }
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == SearchViewModel::class.java)
        return SearchViewModel(searchUseCase) as T
    }

}