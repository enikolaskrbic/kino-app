package com.app.kinogame.ui.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.ui.navigation.NavigationController


open class BaseFragment : Fragment() {
    lateinit var mNavigationController: NavigationController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavigationController = (activity as BaseActivity).getNavigationController()
    }

    open fun showProgress(show: Boolean) {
        (activity as? BaseActivity)?.showProgress(show)
    }

    fun showError(error: GlobalError) {
        (activity as BaseActivity?)?.showError(error)
    }

    fun showMessage(message: String) {
        (activity as BaseActivity?)?.showToast(message)
    }

    fun activity() = activity as? BaseActivity
}