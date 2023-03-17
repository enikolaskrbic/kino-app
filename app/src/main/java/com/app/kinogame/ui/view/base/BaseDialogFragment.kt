package com.app.kinogame.ui.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.ui.navigation.NavigationController

open class BaseDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {

            }
        }
    }

    lateinit var navigationController: NavigationController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = (activity as BaseActivity).getNavigationController()
    }

    open fun showProgress(show: Boolean) {
        (activity as? BaseActivity)?.showProgress(show)
    }

    fun showError(error: GlobalError) {
        (activity as BaseActivity?)?.showError(error)
    }

    fun showMessage(message: String, isError: Boolean) {
        (activity as BaseActivity?)?.showToast(message)
    }

    fun activity() = activity as? BaseActivity
}
