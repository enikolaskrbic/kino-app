package com.app.kinogame.ui.view.upcominggames

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.kinogame.databinding.FragmentUpcomingGamesBinding
import com.app.kinogame.ui.util.observeSingle
import com.app.kinogame.ui.view.base.BaseFragment
import com.app.kinogame.ui.viewmodel.GameViewModel
import com.app.kinogame.ui.viewmodel.TalonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingGamesFragment : BaseFragment() {
    private lateinit var binding: FragmentUpcomingGamesBinding
    private val gameViewModel: GameViewModel by viewModels()
    private val talonViewModel: TalonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initData()
    }

    private fun initView() {
        with(binding){
            recyclerView.itemAnimator = null
            if(recyclerView.adapter == null){
                recyclerView.adapter = KinoUpcomingAdapter { id, time ->
                    onDrawIdClicked(id, time)
                }
            }
        }
    }

    private fun onDrawIdClicked(drawId: Int, drawTime: Long) {
        talonViewModel.resetTalon()
        mNavigationController.navigateToGameDetailsFragment(drawId, drawTime)
    }

    private fun initObservers() {
        observeSingle(gameViewModel.fetchUpcomingGamesState, {

        }, onError = {
            showError(it)
        })

        observeSingle(gameViewModel.getUpcomingGamesLiveData, {
            (binding.recyclerView.adapter as? KinoUpcomingAdapter)?.submitList(it)
        })
    }

    private fun initData() {
        gameViewModel.fetchUpcomingGames()
        gameViewModel.updateTimes()
        gameViewModel.listenUpcomingGames()
    }
}