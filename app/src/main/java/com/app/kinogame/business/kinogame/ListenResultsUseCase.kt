package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseFlow
import com.app.kinogame.data.repository.GameResultRepository
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenResultsUseCase @Inject constructor(private val gameResultRepository: GameResultRepository): UseCaseFlow<Unit, State<List<GameResultUI>>>() {
    override suspend fun invoke(value: Unit): Flow<State<List<GameResultUI>>> {
        return gameResultRepository.getResults()
    }
}