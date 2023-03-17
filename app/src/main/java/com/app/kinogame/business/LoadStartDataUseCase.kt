package com.app.kinogame.business

import com.app.kinogame.business.talon.ResetTalonUseCase
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class LoadStartDataUseCase @Inject constructor(
    private val resetTalonUseCase: ResetTalonUseCase): UseCaseAsync<Unit, Unit>() {
    override suspend fun invoke(value: Unit): State<Unit> {
        resetTalonUseCase.invoke(Unit)
        return SuccessState()
    }
}