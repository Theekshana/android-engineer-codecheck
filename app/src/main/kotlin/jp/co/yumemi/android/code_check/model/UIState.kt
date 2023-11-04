package jp.co.yumemi.android.code_check.model

sealed class UIState<out T> {

    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
    data object Loading : UIState<Nothing>()

}
