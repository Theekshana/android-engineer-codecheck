package jp.co.yumemi.android.code_check.model

/**
 * A sealed class that represents different states of a user interface (UI) operation or request.
 * It is often used to encapsulate the outcome of asynchronous operations.
 *
 * @param T The type of data that may be associated with the UI state.
 */
sealed class UIState<out T> {

    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
    data object Loading : UIState<Nothing>()

}
