package com.app.kinogame.business.talon

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.enum.TalonEnum.GAME_ON_TALON
import com.app.kinogame.data.repository.TalonRepository
import com.app.kinogame.data.room.model.TalonRoom
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class ResetTalonUseCase @Inject constructor(private val talonRepository: TalonRepository): UseCaseAsync<Unit, Unit>() {
    override suspend fun invoke(value: Unit): State<Unit> {
        talonRepository.save(Array(GAME_ON_TALON) { i -> ((i + 1)) }.map { TalonRoom(it, false) })
        return SuccessState(Unit)
    }
}