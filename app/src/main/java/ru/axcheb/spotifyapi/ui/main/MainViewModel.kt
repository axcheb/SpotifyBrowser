package ru.axcheb.spotifyapi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.axcheb.spotifyapi.data.model.Category
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val allCategoriesUseCaseProvider: Provider<AllCategoriesUseCase>
) : ViewModel() {

    private var categoriesPagingSource: PagingSource<*, *>? = null

    private val categoryPager = Pager(PagingConfig(20, enablePlaceholders = false)) {
        categoriesPagingSource?.invalidate()
        val categoriesUseCase = allCategoriesUseCaseProvider.get()
        categoriesUseCase().also { categoriesPagingSource = it }
    }

    val categories: StateFlow<PagingData<Category>> = categoryPager.flow.stateIn(
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