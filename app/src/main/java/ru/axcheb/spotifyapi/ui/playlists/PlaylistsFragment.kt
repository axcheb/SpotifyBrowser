package ru.axcheb.spotifyapi.ui.playlists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artemchep.bindin.bindIn
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.databinding.PlaylistsFragmentBinding
import ru.axcheb.spotifyapi.ui.SpotifyLoadStateAdapter
import javax.inject.Inject

class PlaylistsFragment : Fragment() {

    private val args: PlaylistsFragmentArgs by navArgs()

    private var _binding: PlaylistsFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var factory: PlaylistsViewModelFactory.Factory
    private val viewModel: PlaylistsViewModel by viewModels {
        factory.create(
            args.categoryId
        )
    }

    private val playlistsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PlaylistsAdapter()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistsFragmentBinding.inflate(inflater, container, false).apply {
            playlists.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePlaylists()
        setListeners()
    }

    private fun observePlaylists() {
        binding.playlists.adapter = playlistsAdapter
            .withLoadStateHeaderAndFooter(
                header = SpotifyLoadStateAdapter(),
                footer = SpotifyLoadStateAdapter()
            )
        viewLifecycleOwner.bindIn(viewModel.playlists) {
            playlistsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setListeners() {
        playlistsAdapter.addLoadStateListener { state ->
            with(binding) {
                playlists.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.playlists.adapter = null
        _binding = null
    }

}