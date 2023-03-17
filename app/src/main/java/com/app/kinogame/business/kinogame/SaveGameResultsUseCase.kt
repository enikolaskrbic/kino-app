package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.repository.GameResultRepository
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class SaveGameResultsUseCase @Inject constructor(private val gameResultRepository: GameResultRepository): UseCaseAsync<List<GameResultGroupRoom>, Unit>() {
    override suspend fun invoke(value: List<GameResultGroupRoom>): State<Unit> {
        val result = gameResultRepository.saveResults(value)
        if(result is ErrorState) return ErrorState(result.error)
        return SuccessState()
    }
}