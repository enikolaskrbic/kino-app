package com.app.kinogame.business.kinogame

import com.app.kinogame.business.UseCaseAsync
import com.app.kinogame.data.api.dto.KinoResultRequest
import com.app.kinogame.data.repository.KinoGameRepository
import com.app.kinogame.data.room.model.GameResultGroupRoom
import com.app.kinogame.data.room.model.KinoGameRoom
import com.app.kinogame.data.room.model.NumberRoom
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.SuccessState
import javax.inject.Inject

class FetchResultGameUseCase  @Inject constructor(
    private val kinoGameRepository: KinoGameRepository,
    private val saveGameResultsUseCase: SaveGameResultsUseCase,
) : UseCaseAsync<KinoResultRequest, Unit>() {
    override suspend fun invoke(value: KinoResultRequest): State<Unit> {
        val response = kinoGameRepository.fetchResults(value)
        if(response is ErrorState) return ErrorState(response.error)
        val result = (response as SuccessState).data?: emptyList()
        val resultGroups = mutableListOf<GameResultGroupRoom>()
        result.forEachIndexed { index, game ->
            val bonuses = game.winningNumbers?.bonus?: emptyList()
            val partitions: List<List<Int>> = (game.winningNumbers?.list?: emptyList()).chunked(7)
            partitions.forEachIndexed { i, list ->
                if(i == 0){
                    resultGroups.add(generateGroup(game, emptyList(), 0, true))
                }
                resultGroups.add(generateGroup(game, list.map { NumberRoom(it, bonuses.contains(it)) }, i + 1, false))
            }
        }
        return saveGameResultsUseCase.invoke(resultGroups)
    }

    private fun generateGroup(game: KinoGameRoom, numbers: List<NumberRoom>, order: Int, header: Boolean): GameResultGroupRoom{
        return GameResultGroupRoom(
            "${game.drawId}-${order}",
            game.drawId,
            game.gameId,
            game.drawTime,
            order,
            numbers,
            header
        )
    }
}