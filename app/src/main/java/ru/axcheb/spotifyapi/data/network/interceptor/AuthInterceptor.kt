package ru.axcheb.spotifyapi.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.axcheb.spotifyapi.data.AccessTokenProvider

class AuthInterceptor(private val accessTokenProvider: AccessTokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = accessTokenProvider.getToken()
        val request = chain.request().newBuilder()
            .header(
                ACCESS_TOKEN_HEADER,
                "$BEARER $accessToken"
            )
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val ACCESS_TOKEN_HEADER = "Authorization"
        const val BEARER = "Bearer"
    }
}