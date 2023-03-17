package com.app.kinogame.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.app.kinogame.business.talon.ListenTalonUseCase
import com.app.kinogame.business.talon.ResetTalonUseCase
import com.app.kinogame.business.talon.SetRandomTalonUseCase
import com.app.kinogame.business.talon.TalonItemChangedUseCase
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.data.wrapper.StateHandler
import com.app.kinogame.ui.util.asStateHandlerLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalonViewModel @Inject constructor(
    private val resetTalonUseCase: ResetTalonUseCase,
    private val listenTalonUseCase: ListenTalonUseCase,
    private val talonItemChangedUseCase: TalonItemChangedUseCase,
    private val setRandomTalonUseCase: SetRandomTalonUseCase,
) : ViewModel() {

    private val _getTalonTrigger = MutableLiveData<Unit>()
    val getTalonLiveData: LiveData<StateHandler<List<TalonUI>>> =
        Transformations.switchMap(_getTalonTrigger) {
            liveData { emitSource(listenTalonUseCase.invoke(it).asStateHandlerLiveData()) }
        }

    fun getTalon(){
        _getTalonTrigger.postValue(Unit)
    }

    fun setRandomTalon(number: Int){
        viewModelScope.launch(Dispatchers.IO) {
            setRandomTalonUseCase.invoke(number)
        }
    }

    fun resetTalon(){
        viewModelScope.launch(Dispatchers.IO) {
            resetTalonUseCase.invoke(Unit)
        }
    }

    fun updateTalonItem(talonUI: TalonUI){
        viewModelScope.launch(Dispatchers.IO) {
            talonItemChangedUseCase.invoke(talonUI)
        }
    }
}
