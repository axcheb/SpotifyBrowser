package ru.axcheb.spotifyapi.data.model

interface SearchableEntity : StrIdAware {

    fun getType(): Int

    companion object {
        const val ALBUM = 1
        const val TRACK = 2
        const val PLAYLIST = 3
        const val ARTIST = 4
    }
}