package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class SaveKinoGameUseCase @Inject constructor(private val kinoGameRepository: KinoGameRepository): UseCaseAsync<List<KinoGameRoom>, Unit>() {
    override suspend fun invoke(value: List<KinoGameRoom>): State<Unit> {
        val result = kinoGameRepository.saveUpcomingGames(value)
        if(result is ErrorState) return ErrorState(result.error)
        return SuccessState()
    }
}