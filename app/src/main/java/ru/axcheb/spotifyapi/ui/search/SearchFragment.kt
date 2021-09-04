package ru.axcheb.spotifyapi.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.artemchep.bindin.bindIn
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.mapLatest
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.databinding.SearchFragmentBinding
import ru.axcheb.spotifyapi.ui.SpotifyLoadStateAdapter
import javax.inject.Inject
import javax.inject.Provider

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelProvider: Provider<SearchViewModelFactory>
    private val viewModel: SearchViewModel by viewModels { viewModelProvider.get() }

    private val searchResultAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchResultAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.appComponent?.inject(this)
        observeSearchResults()
        setListeners()
    }

    private fun observeSearchResults() {
        binding.results.adapter = searchResultAdapter
            .withLoadStateHeaderAndFooter(
                header = SpotifyLoadStateAdapter(),
                footer = SpotifyLoadStateAdapter()
            )

        viewLifecycleOwner.bindIn(viewModel.searchResults.mapLatest { it }.buffer(0)) {
            searchResultAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setListeners() {
        binding.query.doAfterTextChanged { text ->
            viewModel.setQuery(text?.toString() ?: "")
        }

        searchResultAdapter.addLoadStateListener { state ->
            with(binding) {
                results.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.results.adapter = null
    }
}