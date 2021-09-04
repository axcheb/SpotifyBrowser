package ru.axcheb.spotifyapi.data.model

data class Track(
    override val id: String,
    val name: String,
    val albumName: String?,
    val albumImage: String?,
    val durationMs: Int,
    val artists: List<Artist> = emptyList()
) : SearchableEntity {
    fun artistStr(): String = artists.joinToString { it.name }
    override fun getType(): Int = SearchableEntity.TRACK
}
