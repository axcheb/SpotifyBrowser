package ru.axcheb.spotifyapi.data.network.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import ru.axcheb.spotifyapi.data.model.Album
import ru.axcheb.spotifyapi.data.model.Artist
import ru.axcheb.spotifyapi.data.model.Playlist
import ru.axcheb.spotifyapi.data.model.SearchableEntity
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.toAlbum
import ru.axcheb.spotifyapi.data.network.toArtist
import ru.axcheb.spotifyapi.data.network.toPlaylist
import ru.axcheb.spotifyapi.data.network.toTrack
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val newReleasesPagingSource: NewReleasesPagingSource,
    private val playlistPagingSourceFactory: PlaylistsPagingSource.Factory,
    private val searchPagingSourceFactory: SearchPagingSource.Factory,
    private val spotifyService: SpotifyService
) {

    fun newReleases(): PagingSource<Int, Album> {
        return newReleasesPagingSource
    }

    fun playlists(categoryId: String): PagingSource<Int, Playlist> {
        return playlistPagingSourceFactory.create(categoryId)
    }

    fun search(query: String): PagingSource<Int, SearchableEntity> {
        return searchPagingSourceFactory.create(query)
    }

    fun playlist(playlistId: String): Flow<Result<Playlist>> {
        return flow {
            val result = try {
                val response = spotifyService.playlist(playlistId)
                if (response.isSuccessful) {
                    val playlistDto = response.body()
                    Result.success(playlistDto!!.toPlaylist())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun album(albumId: String): Flow<Result<Album>> {
        return flow {
            val result = try {
                val response = spotifyService.album(albumId)
                if (response.isSuccessful) {
                    val albumDto = response.body()
                    Result.success(albumDto!!.toAlbum())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun artist(artistId: String): Flow<Result<Artist>> {
        return flow {
            val result = try {
                val artistResp = spotifyService.artist(artistId)
                val trackRes = spotifyService.topTracks(artistId)
                val relatedArtistsResp = spotifyService.relatedArtists(artistId)
                if (artistResp.isSuccessful && trackRes.isSuccessful && relatedArtistsResp.isSuccessful) {
                    val artist = artistResp.body()!!.toArtist()
                    val tracks = trackRes.body()!!.tracks.map { it.toTrack() }
                    val artists = relatedArtistsResp.body()!!.artists.map { it.toArtist() }
                    Result.success(artist.copy(tracks = tracks, relatedArtists = artists))
                } else {
                    Result.failure(HttpException(if (!artistResp.isSuccessful) artistResp else if (!trackRes.isSuccessful) trackRes else relatedArtistsResp))
                }
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
    }


}