package ru.axcheb.spotifyapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OAuthAccessTokenResponseDto(
    /** An access token that can be provided in subsequent calls, for example to Spotify Web API services. */
    @SerialName("access_token") val accessToken: String,
    /** How the access token may be used: always “Bearer”. */
    @SerialName("token_type") val tokenType: String,
    /** A space-separated list of scopes which have been granted for this access_token. */
    @SerialName("scope") val scope: String? = null,
    /** The time period (in seconds) for which the access token is valid. */
    @SerialName("expires_in") val expiresIn: Int? = null,
    /** A token that can be sent to the Spotify Accounts service in place of an authorization code. (When the access code expires, send a POST request to the Accounts service /api/token endpoint, but use this code in place of an authorization code. A new access token will be returned. A new refresh token might be returned too.) */
    @SerialName("refresh_token") val refreshToken: String
)
