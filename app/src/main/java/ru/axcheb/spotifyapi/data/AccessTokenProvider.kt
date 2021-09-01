package ru.axcheb.spotifyapi.data

import android.content.SharedPreferences
import androidx.core.content.edit
import retrofit2.Call
import retrofit2.Response
import ru.axcheb.spotifyapi.BuildConfig
import ru.axcheb.spotifyapi.data.network.model.OAuthAccessTokenResponseDto
import ru.axcheb.spotifyapi.data.network.service.TokenService
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

interface AccessTokenProvider {

    /**
     * Return Access token.
     */
    fun getToken(): String?


    /**
     * Refresh tokens and return Access token.
     */
    fun refreshToken(): String?

    suspend fun receiveToken(code: String)

}

class AccessTokenProviderImpl @Inject constructor(
    private val pref: SharedPreferences,
    private val tokenService: TokenService
) : AccessTokenProvider {

    override fun getToken(): String? {
        return pref.getString(ACCESS_TOKEN, null)
    }

    override fun refreshToken(): String? {
        val token = pref.getString(REFRESH_TOKEN, null) ?: return null
        val tokenCall = tokenService.refreshToken(
            refreshToken = token,
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET
        )
        return executeTokenCall(tokenCall)
    }

    override suspend fun receiveToken(code: String) {
        val tokenResponse = tokenService.authViaCode(
            code = code,
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET
        )
        saveFromResponse(tokenResponse)
    }

    private fun executeTokenCall(tokenCall: Call<OAuthAccessTokenResponseDto>): String? {
        return try {
            val tokenResponse = tokenCall.execute()
            saveFromResponse(tokenResponse)
        } catch (e: RuntimeException) {
            Timber.e(e)
            return null
        } catch (e: IOException) {
            Timber.e(e)
            null
        }
    }

    private fun saveFromResponse(tokenResponse: Response<OAuthAccessTokenResponseDto>): String? {
        if (tokenResponse.isSuccessful) {
            val tokenDto = tokenResponse.body() ?: return null
            pref.edit(true) {
                putString(ACCESS_TOKEN, tokenDto.accessToken)
                if (!tokenDto.refreshToken.isNullOrEmpty()) {
                    putString(REFRESH_TOKEN, tokenDto.refreshToken)
                }
            }
            return tokenDto.accessToken
        }
        return null
    }

    companion object {
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN = "access_token"
    }

}