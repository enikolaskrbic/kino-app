package com.app.kinogame.ui.view.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kinogame.R
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.databinding.FragmentGameDetailsBinding
import com.app.kinogame.databinding.FragmentUpcomingGamesBinding
import com.app.kinogame.ui.util.DateUtil
import com.app.kinogame.ui.util.observeSingle
import com.app.kinogame.ui.view.base.BaseFragment
import com.app.kinogame.ui.viewmodel.GameViewModel
import com.app.kinogame.ui.viewmodel.TalonViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import java.util.Date

@AndroidEntryPoint
class GameDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentGameDetailsBinding
    private var drawId: Int = 0
    private var drawTime: Long = 0
    private val gameViewModel: GameViewModel by viewModels()
    private val talonViewModel: TalonViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            drawId = it.getInt(DRAW_ID, 0)
            drawTime = it.getLong(DRAW_TIME, 0)
        }
        initLabels()
        initView()
        initObservers()
        initData()
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(context, 8)
        if (binding.recyclerView.adapter == null) {
            binding.recyclerView.adapter = TalonAdapter {
                onTalonEvent(it)
            }
        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = null

        binding.buttonRandom.setOnClickListener {
            mNavigationController.navigateToSelectNumber()
        }

        binding.buttonLive.setOnClickListener {
            mNavigationController.navigateToLive(gameViewModel.getLiveLink())
        }

        binding.buttonResults.setOnClickListener {
            mNavigationController.navigateToResults()
        }
    }

    private fun onTalonEvent(it: TalonUI) {
        talonViewModel.updateTalonItem(it)
    }

    private fun initData() {
        gameViewModel.fetchResultsGames()
        gameViewModel.getActiveGame()
        talonViewModel.getTalon()
    }

    private fun initObservers() {
        observeSingle(talonViewModel.getTalonLiveData, {
            (binding.recyclerView.adapter as TalonAdapter?)?.submitList(it)
            binding.textViewSelected.text = String.format(getString(R.string.label_selected), (it?.filter { it.selected }?.size?:0).toString())
        })

        observeSingle(gameViewModel.getActiveGameLiveData, {
            it?.let {
                if(drawId != it.drawId){
                    drawId = it.drawId
                    drawTime = it.drawTime
                    gameViewModel.fetchResultsGames()
                }
                initLabels()
                initTimeLabel(DateUtil.format(it.diff()))
            }
        })
    }

    private fun initTimeLabel(date: String) {
        binding.textViewTime.text = date
    }

    private fun initLabels() {
        with(binding){
            textViewTitle.text = String.format(getString(R.string.label_title_game_details), DateUtil.formatTime(drawTime), drawId.toString())
        }
    }

    companion object {
        const val DRAW_ID = "DRAW_ID"
        const val DRAW_TIME = "DRAW_TIME"
    }
}