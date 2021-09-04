package ru.axcheb.spotifyapi.data.model

data class Track(
    override val id: String,
    val name: String,
    val albumName: String?,
    val albumImage: String?,
    val durationMs: Int,
    val artists: List<Artist> = emptyList()
) : StrIdAware {
    fun artistStr(): String = artists.joinToString { it.name }
}
