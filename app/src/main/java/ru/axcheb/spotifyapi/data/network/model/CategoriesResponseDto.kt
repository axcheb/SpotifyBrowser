package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDto(
    @SerialName("items") val items: List<CategoryDto>,
    @SerialName("href") val href: String? = null,
    @SerialName("limit") val limit: Int? = null,
    @SerialName("offset") val offset: Int? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
)

@Serializable
data class CategoriesResponseWrapperDto(
    @SerialName("categories") val categories: CategoriesResponseDto
)


