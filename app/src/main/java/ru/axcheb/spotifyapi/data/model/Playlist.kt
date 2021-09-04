package ru.axcheb.spotifyapi.data.model

data class Playlist(
    override val id: String,
    val name: String,
    val description: String? = null,
    val iconUrl: String?,
    val tracks: List<Track> = emptyList()
) : SearchableEntity {
    override fun getType(): Int = SearchableEntity.PLAYLIST
}
