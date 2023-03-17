package com.app.kinogame.ui.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.ui.navigation.NavigationController

open class BaseActivity : AppCompatActivity() {

    private lateinit var navigationController: NavigationController

    fun initNavigationController() {
        navigationController = NavigationController(this)
    }

    fun getNavigationController() = navigationController

    fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

    fun showProgress(isLoading: Boolean){

    }

    fun showError(error: GlobalError){
        when(error){
            is GlobalError.ApiError -> {
                showToast("ApiError")
            }
            is GlobalError.UnauthorizedError -> {
                showToast("UnauthorizedError")
            }
            is GlobalError.UnknownBackendError -> {
                showToast("UnknownBackendError")
            }
            is GlobalError.ExceptionError -> {
                showToast("ExceptionError")
            }
            is GlobalError.DatabaseError -> {
                showToast("DatabaseError")
            }
        }
    }
}