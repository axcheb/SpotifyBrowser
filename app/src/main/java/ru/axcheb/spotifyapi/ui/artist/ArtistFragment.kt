package ru.axcheb.spotifyapi.ui.artist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.artemchep.bindin.bindIn
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.databinding.ArtistFragmentBinding
import ru.axcheb.spotifyapi.ui.TracksAdapter
import ru.axcheb.spotifyapi.ui.circularPlaceholder
import timber.log.Timber
import javax.inject.Inject

class ArtistFragment : Fragment() {

    private val args: ArtistFragmentArgs by navArgs()

    private var _binding: ArtistFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var factory: ArtistViewModelFactory.Factory
    private val viewModel: ArtistViewModel by viewModels {
        factory.create(
            args.artistId
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
        _binding = ArtistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeArtist()
        setListeners()
    }

    private fun observeArtist() {
        binding.tracks.adapter = tracksAdapter
        viewLifecycleOwner.bindIn(viewModel.artist) { result ->
            result.onSuccess { artist ->
                binding.tracks.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE

                tracksAdapter.submitList(artist.tracks)
                binding.toolbar.title = artist.name
                binding.icon.load(artist.iconUrl) {
                    placeholder(binding.icon.circularPlaceholder())
                }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun setListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.tracks.adapter = null
        _binding = null
    }

}