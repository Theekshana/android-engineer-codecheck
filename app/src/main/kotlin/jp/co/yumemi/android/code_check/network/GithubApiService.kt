package jp.co.yumemi.android.code_check.network

import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.model.ServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Interface defining the GitHub API service.
 */
interface GithubApiService {
    /**
     * @param q The search query string.
     */
    @Headers("Accept: application/vnd.github.v3+json")
    @GET(Constants.END_POINT_REPOSITORIES)
    suspend fun getRepositories(@Query("q") q: String): Response<ServerResponse>
}