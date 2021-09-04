package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesItemsWrapperDto(
    @SerialName("categories") val categories: ItemsDto<CategoryDto>
)



