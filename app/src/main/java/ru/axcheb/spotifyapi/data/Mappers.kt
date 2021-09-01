package ru.axcheb.spotifyapi.data.network

import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.model.CategoryDto

internal fun CategoryDto.toCategory(): Category {
    val firstIconDto = this.icons.firstOrNull()

    return Category(
        id = this.id,
        name = this.name,
        iconUrl = firstIconDto?.url
    )
}