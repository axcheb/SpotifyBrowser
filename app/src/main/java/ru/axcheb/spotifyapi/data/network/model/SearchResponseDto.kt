package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("albums") val albums: ItemsDto<AlbumDto>? = null,
    @SerialName("tracks") val tracks: ItemsDto<TrackDto>? = null,
    @SerialName("playlists") val playlists: ItemsDto<PlaylistDto>? = null,
    @SerialName("artists") val artists: ItemsDto<ArtistDto>? = null,
)