package jp.co.yumemi.android.code_check.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import jp.co.yumemi.android.code_check.R

/**
 * A DialogFragment to display an error dialog.
 */
class NoInternetErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.error_dialog_fragment, null)
        val builder = AlertDialog.Builder(requireContext())

        val okButton = view.findViewById<Button>(R.id.btnRetry)
        okButton.setOnClickListener {
            dialog?.dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}

class EmptySearchDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.user_input_error_dialog_fragment, null)
        val builder = AlertDialog.Builder(requireContext())

        val okButton = view.findViewById<Button>(R.id.btnOk)
        okButton.setOnClickListener {
            dialog?.dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

}