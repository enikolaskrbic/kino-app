package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.api.dto.KinoUpcomingRequest
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject

class FetchKinoGameUseCase @Inject constructor(
    private val kinoGameRepository: KinoGameRepository,
    private val saveKinoGameUseCase: SaveKinoGameUseCase
) : UseCaseAsync<KinoUpcomingRequest, Unit>() {
    override suspend fun invoke(value: KinoUpcomingRequest): State<Unit> {
        return fetch(value, 0)
    }

    private suspend fun fetch(value: KinoUpcomingRequest, delayMS: Long): State<Unit>{
        delay(delayMS)
        val response = kinoGameRepository.fetchUpcoming(value)
        if (response is ErrorState) return ErrorState(response.error)
        val resultList = (response as SuccessState).data?: emptyList()
        kinoGameRepository.deleteOldGames(resultList.map { it.drawId })
        val saveResponse = saveKinoGameUseCase.invoke(resultList)
        if(saveResponse is ErrorState) return ErrorState(saveResponse.error)

        if(resultList.isEmpty()) return SuccessState()
        val firstTime = resultList[0].drawTime
        val nextFetch = firstTime - Date().time + 1000
        return fetch(value, nextFetch)
    }
}