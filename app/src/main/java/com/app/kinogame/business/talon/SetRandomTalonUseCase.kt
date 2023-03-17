package com.app.kinogame.business.talon

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.enum.TalonEnum.GAME_ON_TALON
import com.app.kinogame.data.repository.TalonRepository
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import java.util.Date
import javax.inject.Inject

class SetRandomTalonUseCase @Inject constructor(private val talonRepository: TalonRepository): UseCaseAsync<Int,Unit>() {
    override suspend fun invoke(value: Int): State<Unit> {
        talonRepository.resetTalon()
        val randomBalls = Array(GAME_ON_TALON) { i -> ((i + 1)) }.toList().shuffled()
        val selectedTalon = randomBalls.subList(0, value).map { TalonRoom(it, true, Date().time) }
        talonRepository.save(selectedTalon)
        return SuccessState()
    }

}