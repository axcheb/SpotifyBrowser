package ru.axcheb.spotifyapi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.artemchep.bindin.bindIn
import ru.axcheb.spotifyapi.appComponent
import ru.axcheb.spotifyapi.data.AccessTokenProvider
import ru.axcheb.spotifyapi.databinding.MainFragmentBinding
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelProvider: Provider<MainViewModel.Factory>
    private val viewModel: MainViewModel by viewModels { viewModelProvider.get() }

    private val categoriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoriesAdapter(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.appComponent?.inject(this)

        binding.categories.adapter = categoriesAdapter.withLoadStateHeaderAndFooter(
            header = CategoriesLoadStateAdapter(),
            footer = CategoriesLoadStateAdapter()
        )

        this.bindIn(viewModel.categories) {
            categoriesAdapter.submitData(this.lifecycle, it)
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
        _binding = null
    }

}