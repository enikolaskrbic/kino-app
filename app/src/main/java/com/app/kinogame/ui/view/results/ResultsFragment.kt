package com.app.kinogame.ui.view.results

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.kinogame.R
import com.app.kinogame.databinding.FragmentResultsBinding
import com.app.kinogame.ui.util.observeSingle
import com.app.kinogame.ui.viewmodel.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentResultsBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initData()
    }

    private fun initView() {
        with(binding){
            if(recyclerView.adapter == null){
                recyclerView.adapter = ResultAdapter()
            }
        }
    }

    private fun initObservers() {
        observeSingle(gameViewModel.getResultsGameLiveData, {
            (binding.recyclerView.adapter as? ResultAdapter)?.submitList(it)
        })
    }

    private fun initData() {
        gameViewModel.getResults()
    }
}