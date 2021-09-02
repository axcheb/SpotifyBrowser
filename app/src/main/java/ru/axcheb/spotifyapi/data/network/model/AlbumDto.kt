package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("id") val id: String,
    @SerialName("album_type") val albumType: String,
    @SerialName("images") val images: List<ImageDto>,
    @SerialName("name") val name: String,
)
