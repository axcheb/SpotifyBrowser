package ru.axcheb.spotifyapi.data.model

interface StrIdAware {
    val id: String
    override fun equals(other: Any?): Boolean
}