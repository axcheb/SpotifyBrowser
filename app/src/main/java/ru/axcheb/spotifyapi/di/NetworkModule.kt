package ru.axcheb.spotifyapi.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import coil.ImageLoader
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.axcheb.spotifyapi.data.AccessTokenProvider
import ru.axcheb.spotifyapi.data.AccessTokenProviderImpl
import ru.axcheb.spotifyapi.data.network.interceptor.AuthInterceptor
import ru.axcheb.spotifyapi.data.network.interceptor.SpotifyTokenAuthenticator
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import ru.axcheb.spotifyapi.data.network.service.TokenService
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json(Json.Default) {
            ignoreUnknownKeys = true
        }
    }

    /**
     * Spotify authorization service.
     */
    @Singleton
    @Provides
    fun provideTokenService(json: Json): TokenService {
        val httpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit.create(TokenService::class.java)
    }

    @Singleton
    @Provides
    fun provideAccessTokenProvider(
        context: Context,
        tokenService: TokenService
    ): AccessTokenProvider {
        val prefs = context.getSharedPreferences("app_pref", MODE_PRIVATE)
        return AccessTokenProviderImpl(prefs, tokenService)
    }

    /**
     * Spotify API Rest service.
     */
    @Singleton
    @Provides
    fun provideSpotifyService(
        json: Json,
        accessTokenProvider: AccessTokenProvider
    ): SpotifyService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(accessTokenProvider))
            .authenticator(SpotifyTokenAuthenticator(accessTokenProvider))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(SpotifyService::class.java)
    }

    @Singleton
    @Provides
    fun provideImageLoader(application: Application): ImageLoader {
        return ImageLoader(application)
    }

}