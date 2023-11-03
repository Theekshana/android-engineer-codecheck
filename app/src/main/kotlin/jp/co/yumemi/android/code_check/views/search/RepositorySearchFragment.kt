package jp.co.yumemi.android.code_check.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.common.NetworkUtils
import jp.co.yumemi.android.code_check.common.hideKeyboard
import jp.co.yumemi.android.code_check.databinding.RepoListFragmentBinding
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.model.UIState
import jp.co.yumemi.android.code_check.util.GitHubAccountAdapter
import timber.log.Timber


/**
 * Fragment for searching and displaying GitHub repositories.
 */
class RepositorySearchFragment : Fragment() {

    init {
        Timber.tag("SearchFragment")
    }

    private lateinit var binding: RepoListFragmentBinding
    private lateinit var viewModel: SearchRepositoryViewModel
    private lateinit var gitHubAccountAdapter: GitHubAccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepoListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SearchRepositoryViewModel::class.java]
        binding.githubVM = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGitHubAccountAdapter()
        observeGitHubList()

    }

    private fun observeGitHubList() {
        viewModel.gitHubList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Success -> {

                    showGitHubData(uiState.data)

                }

                is UIState.Loading -> {

                }

                is UIState.Error -> {

                    Timber.d("Error message: ${uiState.message}")


                }

            }
        }
    }

    private fun showGitHubData(gitHubAccounts: List<GitHubAccounts>) {

        gitHubAccountAdapter.submitList(gitHubAccounts)
        Timber.d("Data $gitHubAccounts")

    }

    private fun initGitHubAccountAdapter() {
        gitHubAccountAdapter =
            GitHubAccountAdapter(object : GitHubAccountAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubAccounts) {
                    navigateToRepositoryFragment(item)
                }
            })

        binding.recyclerView.adapter = gitHubAccountAdapter
        setupSearchInput()
    }

    private fun setupSearchInput() {

        //Perform a search using search input text
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val userInput = editText.text.toString().trim()

                    performSearchAndHideKeyboard(userInput)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
    }

    private fun performSearchAndHideKeyboard(userInput: String) {

        hideKeyboard(binding.searchInputText)

        if (!NetworkUtils.hasInternetConnection(requireContext())) {
            return
        }

        if (userInput.isNotEmpty()) {

            gitHubAccountAdapter.submitList(emptyList())
            viewModel.searchGithubRepository(userInput)

        } else {

            gitHubAccountAdapter.submitList(emptyList())

        }
    }

    //Navigate to the RepositoryFragment
    fun navigateToRepositoryFragment(item: GitHubAccounts) {
        val action =
            RepositorySearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController()
            .navigate(action)
    }

}


