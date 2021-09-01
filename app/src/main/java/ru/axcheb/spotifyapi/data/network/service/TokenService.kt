package ru.axcheb.spotifyapi.data.network.service

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.axcheb.spotifyapi.data.network.model.OAuthAccessTokenResponseDto

interface TokenService {

    /**
     * @param grantType    Required. As defined in the OAuth 2.0 specification, this field must contain the value "authorization_code".
     * @param code    Required. The authorization code returned from the initial request to the Account /authorize endpoint.
     * @param redirectUri    Required. This parameter is used for validation only (there is no actual redirection). The value of this parameter must exactly match the value of redirect_uri supplied when requesting the authorization code.
     * @param clientId Required. App Client ID.
     * @param clientSecret Required. App Client secret.
     */
    @FormUrlEncoded
    @POST("/api/token")
    suspend fun authViaCode(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String = "https://axcheb.ru/callback/",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Response<OAuthAccessTokenResponseDto>


    /**
     * @param grantType    Required. As defined in the OAuth 2.0 specification, this field must contain the value "refresh_token".
     * @param refreshToken Required. The refresh token returned from the authorization code exchange.
     * @param clientId Required. App Client ID.
     * @param clientSecret Required. App Client secret.
     */
    @FormUrlEncoded
    @POST("/api/token")
    fun refreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Call<OAuthAccessTokenResponseDto>

}