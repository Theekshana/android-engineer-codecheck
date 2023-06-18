package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.ServerResponse
import jp.co.yumemi.android.code_check.network.GithubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class responsible for retrieving GitHub account information from the data source.
 */
class GithubRepository @Inject constructor(
    private val githubApiService: GithubApiService
) {
    /**
     * Get GitHub account information from the data source.
     * @param q The query string to search for repositories.
     * @return The server response, or null if the request was unsuccessful.
     */
    suspend fun getGitHutAccountsFromDataSource(q: String): ServerResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext getResponseFromRemoteService(q)
        }
    }

    private suspend fun getResponseFromRemoteService(q: String): ServerResponse? {
        val response = githubApiService.getRepositories(q)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

}