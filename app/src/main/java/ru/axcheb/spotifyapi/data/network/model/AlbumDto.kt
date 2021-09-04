package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("id") val id: String,
    @SerialName("album_type") val albumType: String,
    @SerialName("images") val images: List<ImageDto> = emptyList(),
    @SerialName("name") val name: String,
    @SerialName("artists") val artists: List<ArtistDto> = emptyList(),
    @SerialName("tracks") val tracks: AlbumTrackDto? = null
)

@Serializable
data class AlbumTrackDto(
    @SerialName("items") val items: List<TrackDto> = emptyList(),
)