package com.app.kinogame.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.kinogame.R
import com.app.kinogame.ui.view.base.BaseActivity
import com.app.kinogame.ui.view.game.GameDetailsFragment
import com.app.kinogame.ui.view.live.ShowUrlDialog
import com.app.kinogame.ui.view.results.ResultsFragment
import com.app.kinogame.ui.view.selectnumber.SelectRandomNumberDialog
import com.app.kinogame.ui.view.upcominggames.UpcomingGamesFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class NavigationController @Inject constructor(private val activity: BaseActivity) {

    private val mContainer = R.id.frame_layout
    private val fragmentManager = activity.supportFragmentManager


    fun navigateToHomeFragment() {
        val fragment = fragmentManager.findFragmentByTag("ListGamesFragment") ?: UpcomingGamesFragment()
        replaceFragment(fragment, "ListGamesFragment")
    }

    fun navigateToGameDetailsFragment(drawId: Int, drawTime: Long) {
        val fragment = fragmentManager.findFragmentByTag("GameDetailsFragment") ?: GameDetailsFragment()
        val args = Bundle()
        args.putInt(GameDetailsFragment.DRAW_ID, drawId)
        args.putLong(GameDetailsFragment.DRAW_TIME, drawTime)
        fragment.arguments = args
        replaceFragment(fragment, "GameDetailsFragment", addToBackStack = true)
    }

    fun navigateToResults(){
        val fragment = ResultsFragment()
        fragment.show(fragmentManager, "ResultsFragment")
    }

    fun navigateToSelectNumber(){
        val fragment = SelectRandomNumberDialog()
        fragment.show(fragmentManager, "SelectRandomNumberDialog")
    }

    fun navigateToLive(url: String){
        val urlDialog = ShowUrlDialog()
        urlDialog.url = url
        urlDialog.show(fragmentManager, "ShowUrlDialog")
    }


    private fun replaceFragment(
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false,
        clearBackStack: Boolean = false
    ) {
        if (clearBackStack) {
            clearBackStack()
        }
        fragmentManager.beginTransaction().apply {
            replace(mContainer, fragment, tag)
            if (addToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    private fun clearBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }


}
