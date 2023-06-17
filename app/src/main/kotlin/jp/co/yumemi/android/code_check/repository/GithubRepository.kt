package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.network.GithubApiService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubApiService: GithubApiService) {
}