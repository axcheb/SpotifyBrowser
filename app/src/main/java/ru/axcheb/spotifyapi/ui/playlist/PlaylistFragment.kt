package ru.axcheb.spotifyapi.ui.playlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.artemchep.bindin.bindIn
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.databinding.PlaylistFragmentBinding
import ru.axcheb.spotifyapi.ui.TracksAdapter
import ru.axcheb.spotifyapi.ui.circularPlaceholder
import timber.log.Timber
import javax.inject.Inject

class PlaylistFragment : Fragment() {

    private val args: PlaylistFragmentArgs by navArgs()

    private var _binding: PlaylistFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var factory: PlaylistViewModelFactory.Factory
    private val viewModel: PlaylistViewModel by viewModels {
        factory.create(
            args.playlistId
        )
    }

    private val tracksAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TracksAdapter()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePlaylist()
    }

    private fun observePlaylist() {
        binding.tracks.adapter = tracksAdapter
        viewLifecycleOwner.bindIn(viewModel.playlist) { result ->
            result.onSuccess { playlist ->
                binding.tracks.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE

                tracksAdapter.submitList(playlist.tracks)
                binding.name.text = playlist.name
                binding.description.text = playlist.description
                binding.icon.load(playlist.iconUrl) {
                    placeholder(binding.icon.circularPlaceholder())
                }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.tracks.adapter = null
        _binding = null
    }

}