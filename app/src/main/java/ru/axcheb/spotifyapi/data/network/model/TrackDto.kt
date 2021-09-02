package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("album") val album: AlbumDto,
//    @SerialName("artists") val artists: List<Any>, //TODO Artist object
    @SerialName("duration_ms") val durationMs: Int
)