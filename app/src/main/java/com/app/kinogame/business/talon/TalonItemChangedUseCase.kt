package com.app.kinogame.business.talon

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.enum.TalonEnum.MAX_SELECTED_ITEM
import com.app.kinogame.data.repository.TalonRepository
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class TalonItemChangedUseCase @Inject constructor(private val talonRepository: TalonRepository): UseCaseAsync<TalonUI, Unit>() {

    override suspend fun invoke(value: TalonUI): State<Unit> {
        talonRepository.updateTalon(value.number, !value.selected)
        val selected = talonRepository.getSelected()
        if(selected.size < (MAX_SELECTED_ITEM + 1)) return SuccessState()
        val diff = selected.size - (MAX_SELECTED_ITEM + 1)
        val sortedByTime = selected.sortedBy { it.changedTime }
        for(i in 0..diff){
            val tempTalon = sortedByTime[i]
            talonRepository.updateTalon(tempTalon.number, false)
        }
        return SuccessState(Unit)
    }
}