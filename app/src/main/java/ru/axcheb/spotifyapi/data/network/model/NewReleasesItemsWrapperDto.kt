package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewReleasesItemsWrapperDto(
    @SerialName("albums") val newReleasesItems: ItemsDto<AlbumDto>
)