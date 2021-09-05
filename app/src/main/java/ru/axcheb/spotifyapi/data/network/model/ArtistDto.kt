package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("images") val images: List<ImageDto> = emptyList(),
    @SerialName("type") val type: String,
)

@Serializable
data class ArtistsDto(
    @SerialName("artists") val artists: List<ArtistDto> = emptyList(),
)