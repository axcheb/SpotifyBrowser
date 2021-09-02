package ru.axcheb.spotifyapi.data.model

data class Playlist(
    val id: String,
    val name: String,
    val description: String? = null,
    val iconUrl: String?,
)
