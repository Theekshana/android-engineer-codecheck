package jp.co.yumemi.android.code_check.views.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.NetworkUtils
import jp.co.yumemi.android.code_check.model.GitHubAccounts
import jp.co.yumemi.android.code_check.model.UIState
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    app: Application,
    private val githubRepository: GithubRepository
) : AndroidViewModel(app) {

    //MutableLiveData containing a list of GitHubAccounts.
    private val _gitHubList = MutableLiveData<UIState<List<GitHubAccounts>>>()
    val gitHubList: LiveData<UIState<List<GitHubAccounts>>> get() = _gitHubList

    /**
     * Perform a search for GitHub repositories
     * @param searchQuery The query string to search for repositories.
     */
    fun searchGithubRepository(searchQuery: String) {

        viewModelScope.launch {
            try {

                if (NetworkUtils.hasInternetConnection(getApplication())) {
                    _gitHubList.postValue(UIState.Loading)

                    // Delay to simulate a loading state
                    delay(1000)

                    // Retrieve the server response from the repository
                    val serverResponse =
                        githubRepository.getGitHutAccountsFromDataSource(searchQuery)
                    if (serverResponse != null) {
                        // Notify the UI with the successful data
                        _gitHubList.postValue(UIState.Success(serverResponse.items))

                    } else {
                        // Notify the UI about a null server response
                        _gitHubList.postValue(UIState.Error("Server response is null"))

                    }

                } else {
                    // Notify the UI about the lack of an internet connection
                    _gitHubList.postValue(UIState.Error("No Internet"))

                }

            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        Timber.e("Network error occurred")
                    }

                    else -> {
                        Timber.e(t, "Unexpected error: ${t.message}")
                    }

                }
            }
        }

    }
}