package ru.axcheb.spotifyapi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.model.Category
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val allCategoriesUseCaseProvider: Provider<AllCategoriesUseCase>,
    private val newReleasesUseCaseProvider: Provider<NewReleasesUseCase>
) : ViewModel() {

    private val categoryPager = Pager(PagingConfig(5, enablePlaceholders = false)) {
        val categoriesUseCase = allCategoriesUseCaseProvider.get()
        categoriesUseCase()
    }

    private val newReleasesPager = Pager(PagingConfig(6, enablePlaceholders = false)) {
        val newReleasesUseCase = newReleasesUseCaseProvider.get()
        newReleasesUseCase()
    }

    val categories: StateFlow<PagingData<Category>> = categoryPager.flow.cachedIn(viewModelScope)
        .stateIn(
            viewModelScope, SharingStarted.Lazily,
            PagingData.empty()
        )

    val newReleases: StateFlow<PagingData<Album>> = newReleasesPager.flow.cachedIn(viewModelScope)
        .stateIn(
            viewModelScope, SharingStarted.Lazily,
            PagingData.empty()
        )

    class Factory @Inject constructor(
        private val viewModelProvider: Provider<MainViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainViewModel::class.java)
            return viewModelProvider.get() as T
        }
    }

}