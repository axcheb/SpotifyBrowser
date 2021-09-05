package ru.axcheb.spotifyapi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artemchep.bindin.bindIn
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.data.AccessTokenProvider
import ru.axcheb.spotifyapi.databinding.MainFragmentBinding
import ru.axcheb.spotifyapi.ui.SpotifyLoadStateAdapter
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelProvider: Provider<MainViewModel.Factory>
    private val viewModel: MainViewModel by viewModels { viewModelProvider.get() }

    private val categoriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoriesAdapter()
    }

    private val newReleasesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NewReleasesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            newReleases.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.appComponent?.inject(this)
        observeCategories()
        observeNewReleases()
        setListeners()
    }

    private fun observeCategories() {
        binding.categories.adapter = categoriesAdapter
            .withLoadStateHeaderAndFooter(
                header = SpotifyLoadStateAdapter(),
                footer = SpotifyLoadStateAdapter()
            )
        viewLifecycleOwner.bindIn(viewModel.categories) {
            categoriesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun observeNewReleases() {
        binding.newReleases.adapter = newReleasesAdapter
            .withLoadStateHeaderAndFooter(
                header = SpotifyLoadStateAdapter(),
                footer = SpotifyLoadStateAdapter()
            )

        viewLifecycleOwner.bindIn(viewModel.newReleases) {
            newReleasesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setListeners() {
        binding.toolbar.setOnMenuItemClickListener {
            if (isAdded) {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
            }
            true
        }

        categoriesAdapter.addLoadStateListener { state ->
            with(binding) {
                categories.isVisible = state.refresh != LoadState.Loading
                categoriesProgress.isVisible = state.refresh == LoadState.Loading
            }
        }

        newReleasesAdapter.addLoadStateListener { state ->
            with(binding) {
                newReleases.isVisible = state.refresh != LoadState.Loading
                newReleasesProgress.isVisible = state.refresh == LoadState.Loading
            }
        }

    }

    @Inject
    fun checkAndNavigateToAuth(accessTokenProvider: AccessTokenProvider) {
        if (accessTokenProvider.getToken() == null) {
            view?.findNavController()
                ?.navigate(MainFragmentDirections.actionMainFragmentToAuthFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.categories.adapter = null
        binding.newReleases.adapter = null
        _binding = null
    }

}