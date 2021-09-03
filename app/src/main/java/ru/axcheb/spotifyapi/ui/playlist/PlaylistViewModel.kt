package ru.axcheb.spotifyapi.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PlaylistViewModel constructor(
    playlistId: String,
    playlistUseCase: PlaylistUseCase
) : ViewModel() {

    val playlist = playlistUseCase(playlistId)

}

@Suppress("UNCHECKED_CAST")
class PlaylistViewModelFactory @AssistedInject constructor(
    @Assisted("playlistId") private val playlistId: String,
    private val playlistUseCase: PlaylistUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == PlaylistViewModel::class.java)
        return PlaylistViewModel(playlistId, playlistUseCase) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("playlistId") playlistId: String): PlaylistViewModelFactory
    }
}
