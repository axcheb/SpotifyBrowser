package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistItemsWrapperDto(
    @SerialName("playlists") val playlistItems: ItemsDto<PlaylistDto>
)
