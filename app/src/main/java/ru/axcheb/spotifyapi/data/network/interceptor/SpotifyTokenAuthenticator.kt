package ru.axcheb.spotifyapi.data.network.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.axcheb.spotifyapi.data.AccessTokenProvider
import ru.axcheb.spotifyapi.data.network.interceptor.AuthInterceptor.Companion.ACCESS_TOKEN_HEADER
import ru.axcheb.spotifyapi.data.network.interceptor.AuthInterceptor.Companion.BEARER

class SpotifyTokenAuthenticator(private val accessTokenProvider: AccessTokenProvider) :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = accessTokenProvider.refreshToken() ?: return null
        return response.request.newBuilder()
            .header(
                ACCESS_TOKEN_HEADER,
                "$BEARER $accessToken"
            )
            .build()
    }

}