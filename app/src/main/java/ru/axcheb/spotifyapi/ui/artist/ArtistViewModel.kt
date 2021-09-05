package ru.axcheb.spotifyapi.ui.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ArtistViewModel(artistId: String, artistUseCase: ArtistUseCase) : ViewModel() {

    val artist = artistUseCase(artistId)

}

@Suppress("UNCHECKED_CAST")
class ArtistViewModelFactory @AssistedInject constructor(
    @Assisted("artistId") private val artistId: String,
    private val artistUseCase: ArtistUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == ArtistViewModel::class.java)
        return ArtistViewModel(artistId, artistUseCase) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("artistId") artistId: String): ArtistViewModelFactory
    }
}