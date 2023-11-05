package jp.co.yumemi.android.code_check.views.repositoryDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.model.GitHubAccounts

/**
 * ViewModel for displaying details of a GitHub repository.
 *
 * This ViewModel is responsible for loading and managing data related to a specific GitHub repository.
 *
 * @property _repositoryList MutableLiveData that holds the GitHub repository details.
 * @property repositoryList LiveData for observing changes in the GitHub repository details.
 */
class RepositoryDetailsViewModel : ViewModel() {

    private val _repositoryList = MutableLiveData<GitHubAccounts>(null)
    val repositoryList: LiveData<GitHubAccounts> get() = _repositoryList

    /**
     * Load the GitHub repository details.
     *
     * @param repositoryList The GitHub repository details to load and display.
     */
    fun loadRepositoryList(repositoryList: GitHubAccounts) {

        _repositoryList.value = repositoryList

    }
}