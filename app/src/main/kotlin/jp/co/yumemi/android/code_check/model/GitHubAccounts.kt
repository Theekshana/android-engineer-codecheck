package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a GitHub account.
 */
@Parcelize
data class GitHubAccounts(
    val name: String?,
    val owner: Owner?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Long?,
    @SerializedName("watchers_count")
    val watchersCount: Long?,
    @SerializedName("forks_count")
    val forksCount: Long?,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long?,
) : Parcelable
