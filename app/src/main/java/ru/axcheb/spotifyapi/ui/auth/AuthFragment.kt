package ru.axcheb.spotifyapi.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.coroutines.*
import ru.axcheb.spotifyapi.BuildConfig
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.data.AccessTokenProvider
import ru.axcheb.spotifyapi.databinding.AuthFragmentBinding
import java.util.*
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    @Inject
    lateinit var accessTokenProvider: AccessTokenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // do nothing
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openAuthorizeWebView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.appComponent?.inject(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openAuthorizeWebView() {
        val uniqueState = UUID.randomUUID().toString()

        binding.authWebView.settings.javaScriptEnabled = true
        binding.authWebView.settings.userAgentString = USER_AGENT_STRING

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            binding.authWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.let { handleRedirectRequest(request.url, uniqueState) }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
        } else {
            binding.authWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    handleRedirectRequest(Uri.parse(url), uniqueState)
                    return super.shouldOverrideUrlLoading(view, url)
                }
            }
        }


        binding.authWebView.loadUrl(buildAuthUri(uniqueState).toString())
    }

    private fun buildAuthUri(uniqueState: String): Uri {
        val clientID = BuildConfig.CLIENT_ID
        return Uri.parse(AUTHORIZATION_URL)
            .buildUpon()
            .appendQueryParameter("client_id", clientID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", uniqueState)
            .build()
    }

    private fun handleRedirectRequest(url: Uri, uniqueState: String) {
        if (!url.toString().startsWith(REDIRECT_URI)) {
            return
        }
        val responseState = url.getQueryParameter("state")
        if (responseState == uniqueState) {
            url.getQueryParameter("code")?.let {
                uiScope.launch(Dispatchers.IO) {
                    accessTokenProvider.receiveToken(it)
                    withContext(Dispatchers.Main) {
                        view?.findNavController()?.navigateUp()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val REDIRECT_URI = "https://axcheb.ru/callback/"
        private const val AUTHORIZATION_URL = "https://accounts.spotify.com/authorize"
        private const val USER_AGENT_STRING = "Chrome/92.0.0 Mobile"
    }

}