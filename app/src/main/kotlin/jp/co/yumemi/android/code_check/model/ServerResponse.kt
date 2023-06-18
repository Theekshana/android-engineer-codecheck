package jp.co.yumemi.android.code_check.model

/**
 *Data class representing the server response for a GitHub search.
 */
data class ServerResponse(
    val total_count: Int?,
    val incomplete_results: Boolean?,
    val items: List<GitHubAccounts>
)
