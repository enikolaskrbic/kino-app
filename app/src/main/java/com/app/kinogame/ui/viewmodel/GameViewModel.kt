package com.app.kinogame.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.app.kinogame.business.kinogame.FetchKinoGameUseCase
import com.app.kinogame.business.kinogame.FetchResultGameUseCase
import com.app.kinogame.business.kinogame.ListenActiveGameUseCase
import com.app.kinogame.business.kinogame.ListenResultsUseCase
import com.app.kinogame.business.kinogame.ListenUpcomingEventUseCase
import com.app.kinogame.business.kinogame.UpdateTimeInGamesUseCase
import com.app.kinogame.data.api.dto.KinoResultRequest
import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.StateHandler
import com.app.kinogame.data.wrapper.SuccessState
import com.app.kinogame.ui.util.DateUtil
import com.app.kinogame.ui.util.asStateHandlerLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val fetchKinoGameUseCase: FetchKinoGameUseCase,
    private val updateTimeInGamesUseCase: UpdateTimeInGamesUseCase,
    private val listenUpcomingEventUseCase: ListenUpcomingEventUseCase,
    private val listenActiveGameUseCase: ListenActiveGameUseCase,
    private val fetchResultGameUseCase: FetchResultGameUseCase,
    private val listenResultsUseCase: ListenResultsUseCase
) : ViewModel() {

    private val _getUpcomingGamesLiveDataTrigger = MutableLiveData<Unit>()
    val getUpcomingGamesLiveData: LiveData<StateHandler<List<KinoUpcomingUI>>> =
        Transformations.switchMap(_getUpcomingGamesLiveDataTrigger) {
            liveData { emitSource(listenUpcomingEventUseCase.invoke(it).asStateHandlerLiveData()) }
        }

    private val _getActiveGameLiveDataTrigger = MutableLiveData<Unit>()
    val getActiveGameLiveData: LiveData<StateHandler<KinoUpcomingUI>> =
        Transformations.switchMap(_getActiveGameLiveDataTrigger) {
            liveData { emitSource(listenActiveGameUseCase.invoke(it).asStateHandlerLiveData()) }
        }

    private val _getResultsLiveDataTrigger = MutableLiveData<Unit>()
    val getResultsGameLiveData: LiveData<StateHandler<List<GameResultUI>>> =
        Transformations.switchMap(_getResultsLiveDataTrigger) {
            liveData { emitSource(listenResultsUseCase.invoke(it).asStateHandlerLiveData()) }
        }

    val fetchUpcomingGamesState = MutableLiveData<StateHandler<Unit>>()
    val fetchResultsState = MutableLiveData<StateHandler<Unit>>()

    fun getActiveGame(){
        _getActiveGameLiveDataTrigger.postValue(Unit)
    }

    fun getResults(){
        _getResultsLiveDataTrigger.postValue(Unit)
    }

    fun fetchUpcomingGames(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchKinoGameUseCase.invoke(KinoUpcomingRequest())
            fetchUpcomingGamesState.postValue(StateHandler(result))
        }
    }

    fun fetchResultsGames(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchResultGameUseCase.invoke(KinoResultRequest())
            fetchResultsState.postValue(StateHandler(result))
        }
    }

    fun updateTimes(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            updateTimeInGamesUseCase.invoke(Unit)
        }
    }

    fun listenUpcomingGames(){
        _getUpcomingGamesLiveDataTrigger.postValue(Unit)
    }

    fun getLiveLink(): String {
        return "https://ds.opap.gr/web_kino/kinoIframe.html?link=https://ds.opap.gr/web_kino/kino/html/Internet_PRODUCTION/KinoDraw_201910.html&resolution=847x500"
    }
}
