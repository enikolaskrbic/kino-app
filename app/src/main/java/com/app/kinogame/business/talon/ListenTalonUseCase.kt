package com.app.kinogame.business.talon

import com.app.kinogame.business.UseCaseFlow
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.repository.TalonRepository
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.data.wrapper.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenTalonUseCase @Inject constructor(private val talonRepository: TalonRepository): UseCaseFlow<Unit, State<List<TalonUI>>>() {
    override suspend fun invoke(value: Unit): Flow<State<List<TalonUI>>> {
        return talonRepository.listenTalon()
    }
}