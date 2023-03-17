package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseFlow
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenActiveGameUseCase @Inject constructor(private val kinoGameRepository: KinoGameRepository): UseCaseFlow<Unit, State<KinoUpcomingUI>>() {
    override suspend fun invoke(value: Unit): Flow<State<KinoUpcomingUI>> {
        return kinoGameRepository.getActiveGame()
    }
}