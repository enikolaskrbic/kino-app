package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseFlow
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenUpcomingEventUseCase @Inject constructor(private val kinoGameRepository: KinoGameRepository): UseCaseFlow<Unit, State<List<KinoUpcomingUI>>>() {
    override suspend fun invoke(value: Unit): Flow<State<List<KinoUpcomingUI>>> {
        return kinoGameRepository.getUpcomingGames()
    }
}