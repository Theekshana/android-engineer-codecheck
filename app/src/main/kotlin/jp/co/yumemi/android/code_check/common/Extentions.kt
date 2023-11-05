package jp.co.yumemi.android.code_check.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hides the software keyboard when called. This function is useful when you want to hide the keyboard
 * after an input operation, such as clicking a button, is performed.
 *
 * @param view The [View] associated with the keyboard input field. The keyboard will be hidden
 * for this view.
 */
fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Makes the [View] visible by setting its visibility to [View.VISIBLE].
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Hides the [View] by setting its visibility to [View.INVISIBLE]. Note that the [View] still
 * occupies space in the layout, but it is not visible.
 */
fun View.hide() {
    visibility = View.INVISIBLE
}
