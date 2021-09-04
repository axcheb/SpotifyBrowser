package ru.axcheb.spotifyapi.ui.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AlbumViewModel(albumId: String, albumUseCase: AlbumUseCase) : ViewModel() {

    val album = albumUseCase(albumId)

}

@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory @AssistedInject constructor(
    @Assisted("albumId") private val albumId: String,
    private val albumUseCase: AlbumUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == AlbumViewModel::class.java)
        return AlbumViewModel(albumId, albumUseCase) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("albumId") albumId: String): AlbumViewModelFactory
    }
}