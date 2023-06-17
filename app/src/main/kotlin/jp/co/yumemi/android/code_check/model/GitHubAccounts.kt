package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a GitHub account.
 */
@Parcelize
data class GitHubAccounts(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
