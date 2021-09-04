package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("album") val album: AlbumDto? = null,
    @SerialName("artists") val artists: List<ArtistDto> = emptyList(),
    @SerialName("duration_ms") val durationMs: Int,
)

@Serializable
data class TrackWrapperDto(
    @SerialName("track") val track: TrackDto
)