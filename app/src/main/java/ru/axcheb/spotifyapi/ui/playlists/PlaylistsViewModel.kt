package ru.axcheb.spotifyapi.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.ui.main.PlaylistsUseCase
import javax.inject.Provider

class PlaylistsViewModel constructor(
    private val categoryId: String,
    private val playlistsUseCase: Provider<PlaylistsUseCase>
) : ViewModel() {

    private val playlistsPager = Pager(PagingConfig(5, enablePlaceholders = false)) {
        val playlistsUseCase = playlistsUseCase.get()
        playlistsUseCase(categoryId)
    }

    val playlists: StateFlow<PagingData<Playlist>> =
        playlistsPager.flow.cachedIn(viewModelScope).stateIn(
            viewModelScope, SharingStarted.Lazily,
            PagingData.empty()
        )
}

class PlaylistsViewModelFactory @AssistedInject constructor(
    @Assisted("categoryId") private val categoryId: String,
    private val playlistsUseCase: Provider<PlaylistsUseCase>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == PlaylistsViewModel::class.java)
        return PlaylistsViewModel(categoryId, playlistsUseCase) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("categoryId") categoryId: String): PlaylistsViewModelFactory
    }

}