package jp.co.yumemi.android.code_check.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.model.ServerResponse
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    val githubRepository: GithubRepository
) : ViewModel() {

    private val _gitHubList = MutableLiveData<List<GitHubAccounts>>(null)
    val gitHubList: LiveData<List<GitHubAccounts>> get() = _gitHubList

    /**
     * Perform a search for GitHub repositories
     * @param q The query string to search for repositories.
     */
    fun searchGithubRepository(q: String) {

        viewModelScope.launch {
            val serverResponse: ServerResponse? =
                githubRepository.getGitHutAccountsFromDataSource(q)

            //_gitHubList.value = serverResponse?.items
        }

    }
}