package ru.axcheb.spotifyapi.data.network

import ru.axcheb.spotifyapi.data.model.*
import ru.axcheb.spotifyapi.data.network.model.*

internal fun CategoryDto.toCategory(): Category {
    val firstIconDto = this.icons.firstOrNull()
    return Category(
        id = this.id,
        name = this.name,
        iconUrl = firstIconDto?.url
    )
}

internal fun AlbumDto.toAlbum(): Album {
    val firstIconDto = this.images.firstOrNull()
    return Album(
        id = this.id,
        albumType = this.albumType,
        name = this.name,
        iconUrl = firstIconDto?.url
    )
}

internal fun PlaylistDto.toPlaylist(): Playlist {
    val firstIconDto = this.images.firstOrNull()
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        iconUrl = firstIconDto?.url,
        tracks = tracks?.items?.map { it.track.toTrack() } ?: emptyList()
    )
}

internal fun ArtistDto.toArtist(): Artist {
    return Artist(id, name)
}

internal fun TrackDto.toTrack(): Track {
    return Track(
        id = id,
        name = name,
        albumName = album.name,
        albumImage = album.images.firstOrNull()?.url,
        durationMs = durationMs,
        artists = artists.map { it.toArtist() },
    )
}
