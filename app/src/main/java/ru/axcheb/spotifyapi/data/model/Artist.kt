package ru.axcheb.spotifyapi.data.model

data class Artist(
    override val id: String,
    val name: String,
) : StrIdAware
