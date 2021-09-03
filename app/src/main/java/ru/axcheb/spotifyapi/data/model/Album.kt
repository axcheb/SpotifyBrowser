package ru.axcheb.spotifyapi.data.model

data class Album(
    override val id: String,
    val albumType: String,
    val iconUrl: String?,
    val name: String,
) : StrIdAware