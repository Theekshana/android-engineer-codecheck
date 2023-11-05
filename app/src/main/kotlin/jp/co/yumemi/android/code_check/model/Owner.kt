package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Parcelable data class representing an owner.
 * @property avatarUrl The URL of the owner's avatar.
 */
@Parcelize
data class Owner(@SerializedName("avatar_url") val avatarUrl: String) : Parcelable