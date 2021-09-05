package ru.axcheb.spotifyapi.data.network.service

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.axcheb.spotifyapi.data.network.model.*

interface SpotifyService {

    /**
     * Get a list of categories used to tag items in Spotify (on, for example, the Spotify player’s “Browse” tab).
     *
     * @param limit The maximum number of categories to return. Default: 20. Minimum: 1. Maximum: 50.
     * @param offset The index of the first item to return. Default: 0 (the first object). Use with limit to get the next set of categories.
     *
     * [Rest API Documentation](https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-categories)
     */
    @GET("/v1/browse/categories")
    suspend fun categories(
        @Query("limit") @IntRange(from = 1) limit: Int = 20,
        @Query("offset") offset: Int
    ): Response<CategoriesItemsWrapperDto>

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).
     *
     * @param limit The maximum number of categories to return. Default: 20. Minimum: 1. Maximum: 50.
     * @param offset The index of the first item to return. Default: 0 (the first object). Use with limit to get the next set of categories.
     *
     * [Rest API Documentation](https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-new-releases)
     */
    @GET("/v1/browse/new-releases")
    suspend fun newReleases(
        @Query("limit") @IntRange(from = 1) limit: Int = 20,
        @Query("offset") offset: Int
    ): Response<NewReleasesItemsWrapperDto>

    /**
     * Get a list of Spotify playlists tagged with a particular category.
     *
     * @param limit The maximum number of categories to return. Default: 20. Minimum: 1. Maximum: 50.
     * @param offset The index of the first item to return. Default: 0 (the first object). Use with limit to get the next set of categories.
     */
    @GET("/v1/browse/categories/{category_id}/playlists")
    suspend fun playlists(
        @Path("category_id") categoryId: String,
        @Query("limit") @IntRange(from = 1) limit: Int = 20,
        @Query("offset") offset: Int
    ): Response<PlaylistItemsWrapperDto>


    /**
     * Get a Playlist.
     */
    @GET("/v1/playlists/{playlist_id}")
    suspend fun playlist(
        @Path("playlist_id") playlistId: String
    ): Response<PlaylistDto>

    /**
     * Get an Album.
     */
    @GET("/v1/albums/{albumId}")
    suspend fun album(
        @Path("albumId") albumId: String
    ): Response<AlbumDto>

    @GET("/v1/artists/{id}")
    suspend fun artist(
        @Path("id") artistId: String
    ): Response<ArtistDto>

    @GET("/v1/artists/{id}/top-tracks")
    suspend fun topTracks(
        @Path("id") artistId: String,
        @Query("market") market: String = DEFAULT_MARKET
    ): Response<TracksListWrapper>

    /**
     * Search for an Item.
     * Get Spotify Catalog information about albums, artists, playlists, tracks, shows or episodes that match a keyword string.
     */
    @GET("/v1/search")
    suspend fun search(
        @Query("query") q: String,
        @Query("limit") @IntRange(from = 1) limit: Int = 20,
        @Query("offset") offset: Int,
        @Query("type") type: String = "album,track,playlist,artist",
    ): Response<SearchResponseDto>

    companion object {
        const val DEFAULT_MARKET = "RU"
        const val DEFAULT_PAGE_SIZE = 20
    }

}