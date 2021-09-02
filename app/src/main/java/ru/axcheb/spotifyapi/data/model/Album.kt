package ru.axcheb.spotifyapi.data.model

data class Album(
    val id: String,
    val albumType: String,
    val iconUrl: String?,
    val name: String,
)