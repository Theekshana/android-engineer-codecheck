package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.ServerResponse
import jp.co.yumemi.android.code_check.network.GithubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository class responsible for retrieving GitHub account information from the data source.
 */
class GithubRepository @Inject constructor(
    private val githubApiService: GithubApiService
) {
    /**
     * Get GitHub account information from the data source.
     * @param searchQuery The query string to search for repositories.
     * @return The server response, or null if the request was unsuccessful.
     */
    suspend fun getGitHutAccountsFromDataSource(searchQuery: String): ServerResponse? {
        return withContext(Dispatchers.IO) {

            val serverResponse = getResponseFromRemoteService(searchQuery)

            if (serverResponse != null) {
                Timber.d("Received a successful server response")
            } else {
                Timber.e("Received an unsuccessful server response")

            }

            return@withContext serverResponse

        }
    }

    private suspend fun getResponseFromRemoteService(searchQuery: String): ServerResponse? {
        val response = githubApiService.getRepositories(searchQuery)
        return if (response.isSuccessful) {

            val responseBody = response.body()
            Timber.d("Received a successful response from remote service: $responseBody")
            response.body()

        } else {

            Timber.e("Received an unsuccessful response from remote service. HTTP Code: ${response.code()}")
            null
            
        }

    }

}