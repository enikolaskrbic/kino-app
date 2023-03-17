package com.app.kinogame

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.app.kinogame.databinding.ActivityMainBinding
import com.app.kinogame.ui.view.base.BaseActivity
import com.app.kinogame.ui.viewmodel.ApplicationViewModel
import com.app.kinogame.ui.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val applicationViewModel: ApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationViewModel.initData()
        initNavigationController()
        getNavigationController().navigateToHomeFragment()
    }
}