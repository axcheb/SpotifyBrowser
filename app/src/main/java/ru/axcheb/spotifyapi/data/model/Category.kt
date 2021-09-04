package ru.axcheb.spotifyapi.data.model

data class Category(
    override val id: String,
    val name: String,
    val iconUrl: String?
) : StrIdAware