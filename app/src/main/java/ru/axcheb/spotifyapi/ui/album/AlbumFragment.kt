package ru.axcheb.spotifyapi.ui.album

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
import ru.axcheb.spotifyapi.databinding.AlbumFragmentBinding
import ru.axcheb.spotifyapi.ui.TracksAdapter
import ru.axcheb.spotifyapi.ui.circularPlaceholder
import timber.log.Timber
import javax.inject.Inject

class AlbumFragment : Fragment() {

    private val args: AlbumFragmentArgs by navArgs()

    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var factory: AlbumViewModelFactory.Factory
    private val viewModel: AlbumViewModel by viewModels {
        factory.create(
            args.albumId
        )
    }

    private val tracksAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TracksAdapter(false)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tracks.adapter = tracksAdapter
        viewLifecycleOwner.bindIn(viewModel.album) { result ->
            result.onSuccess { album ->
                binding.tracks.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE

                tracksAdapter.submitList(album.tracks)
                binding.name.text = album.name
                binding.artists.text = album.artistStr()
                binding.icon.load(album.iconUrl) {
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