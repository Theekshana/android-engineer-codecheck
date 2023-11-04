package jp.co.yumemi.android.code_check.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.common.EmptySearchDialogFragment
import jp.co.yumemi.android.code_check.common.NetworkUtils
import jp.co.yumemi.android.code_check.common.NoInternetErrorDialogFragment
import jp.co.yumemi.android.code_check.common.hide
import jp.co.yumemi.android.code_check.common.hideKeyboard
import jp.co.yumemi.android.code_check.common.show
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

        // Initialize the GitHub account adapter and observe the data
        initGitHubAccountAdapter()
        observeGitHubList()

    }

    /**
     * Observes the GitHub account list and handles different UI states.
     */
    private fun observeGitHubList() {
        viewModel.gitHubList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Success -> {
                    hideProgressBar()
                    showGitHubData(uiState.data)

                }

                is UIState.Loading -> {

                    showProgressBar()

                }

                is UIState.Error -> {
                    showNoInternetErrorDialog()
                    Timber.d("Error message: ${uiState.message}")
                    hideProgressBar()

                }

            }
        }
    }

    /**
     * Displays GitHub account data in the RecyclerView.
     *
     * @param gitHubAccounts The list of GitHub accounts to display.
     */
    private fun showGitHubData(gitHubAccounts: List<GitHubAccounts>) {

        gitHubAccountAdapter.submitList(gitHubAccounts)
        Timber.d("Data $gitHubAccounts")

    }

    /**
     * Initializes the GitHub account adapter and sets up the search input handling.
     */
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

    /**
     * Sets up the search input handling, including search button clicks.
     */
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

    /**
     * Performs a search and hides the keyboard.
     *
     * @param userInput The search input from the user.
     */
    private fun performSearchAndHideKeyboard(userInput: String) {

        hideKeyboard(binding.searchInputText)

        if (!NetworkUtils.hasInternetConnection(requireContext())) {
            showNoInternetErrorDialog()
            return
        }

        if (userInput.isNotEmpty()) {

            gitHubAccountAdapter.submitList(emptyList())
            viewModel.searchGithubRepository(userInput)

        } else {

            gitHubAccountAdapter.submitList(emptyList())
            showEmptySearchDialogFragment()

        }
    }

    /**
     * Displays a dialog for no internet connection.
     */
    private fun showNoInternetErrorDialog() {
        val dialogFragment = NoInternetErrorDialogFragment()
        dialogFragment.show(childFragmentManager, "noInternetErrorDialog")
    }

    /**
     * Displays a dialog for empty search input.
     */
    private fun showEmptySearchDialogFragment() {
        val emptySearch = EmptySearchDialogFragment()
        emptySearch.show(childFragmentManager, "EmptySearchDialog")
    }

    private fun hideProgressBar() {
        binding.lottieProgressBar.hide()


    }

    private fun showProgressBar() {
        binding.lottieProgressBar.apply {
            show()
            playAnimation()
        }
    }

    /**
     * Navigates to the RepositoryFragment with the selected GitHub account item.
     *
     * @param item The selected GitHub account to display.
     */
    fun navigateToRepositoryFragment(item: GitHubAccounts) {
        val action =
            RepositorySearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController()
            .navigate(action)
        Timber.d("Navigated to destination: %s", item)
    }

}


