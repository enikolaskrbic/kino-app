package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject

class UpdateTimeInGamesUseCase @Inject constructor(private val kinoGameRepository: KinoGameRepository): UseCaseAsync<Unit, Unit>(){
    override suspend fun invoke(value: Unit): State<Unit> {
        return updateTime()
    }

    private suspend fun updateTime(): State<Unit> {
        kinoGameRepository.updateCurrentTime(Date().time)
        delay(999)
        return updateTime()
    }

}