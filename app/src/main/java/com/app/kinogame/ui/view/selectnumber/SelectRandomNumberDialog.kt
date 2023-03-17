package com.app.kinogame.ui.view.selectnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kinogame.databinding.DialogChooseRandomNumberBinding
import com.app.kinogame.ui.viewmodel.TalonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectRandomNumberDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogChooseRandomNumberBinding
    private val talonViewModel: TalonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogChooseRandomNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(context, 5)
        if (binding.recyclerView.adapter == null) {
            binding.recyclerView.adapter = SelectNumberAdapter {
                talonViewModel.setRandomTalon(it)
                dismiss()
            }
        }
        binding.recyclerView.layoutManager = layoutManager
        (binding.recyclerView.adapter as? SelectNumberAdapter)?.submitList(Array(15) { i -> ((i + 1)) }.toList())
    }
}