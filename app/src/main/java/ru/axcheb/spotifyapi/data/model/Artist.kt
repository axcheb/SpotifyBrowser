package ru.axcheb.spotifyapi.data.model

data class Artist(
    override val id: String,
    val name: String,
    val iconUrl: String?,
    val type: String?,
    val tracks: List<Track> = emptyList(),
    val relatedArtists: List<Artist> = emptyList()
) : SearchableEntity {

    override fun getType(): Int = SearchableEntity.ARTIST

}
