package com.app.kinogame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kinogame.business.LoadStartDataUseCase
import com.app.kinogame.business.talon.ResetTalonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val loadStartDataUseCase: LoadStartDataUseCase,
) : ViewModel() {

    fun initData(){
        viewModelScope.launch(Dispatchers.IO) {
            loadStartDataUseCase.invoke(Unit)
        }
    }
}
