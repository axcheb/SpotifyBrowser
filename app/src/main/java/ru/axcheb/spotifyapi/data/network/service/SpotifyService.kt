package ru.axcheb.spotifyapi.data.network.service

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.axcheb.spotifyapi.data.network.model.CategoriesResponseWrapperDto

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
    ): Response<CategoriesResponseWrapperDto>

    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 50
    }

}