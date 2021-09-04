package ru.axcheb.spotifyapi.data.model

data class Album(
    override val id: String,
    val albumType: String,
    val iconUrl: String?,
    val name: String,
    val artists: List<Artist> = emptyList(),
    val tracks: List<Track> = emptyList()
) : StrIdAware {
    fun artistStr(): String = artists.joinToString { it.name }
}