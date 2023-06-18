package jp.co.yumemi.android.code_check.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.util.GitHubAccountAdapter

/**
 * Fragment for searching and displaying GitHub repositories.
 */
class RepositorySearchFragment : Fragment() {

    private lateinit var binding: FragmentOneBinding
    private lateinit var viewModel: SearchRepositoryViewModel
    private lateinit var gitHubAccountAdapter: GitHubAccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SearchRepositoryViewModel::class.java]
        binding.githubVM = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitHubAccountAdapter =
            GitHubAccountAdapter(object : GitHubAccountAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubAccounts) {
                    navigateToRepositoryFragment(item)
                }
            })

        binding.recyclerView.adapter = gitHubAccountAdapter

        //Perform a search using search input text
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchGithubRepository(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        //Sets up the changes in the gitHubList
        viewModel.gitHubList.observe(requireActivity()) {
            gitHubAccountAdapter.submitList(it)
        }

    }

    //Navigate to the RepositoryFragment
    fun navigateToRepositoryFragment(item: GitHubAccounts) {
        val action =
            RepositorySearchFragmentDirections
                .actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController()
            .navigate(action)
    }

}


