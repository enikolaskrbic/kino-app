package com.app.kinogame.ui.view.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.app.kinogame.R
import com.app.kinogame.data.uimodel.GameResultUI
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.databinding.RowKinoUpcomingBinding
import com.app.kinogame.databinding.RowResultsBallsBinding
import com.app.kinogame.databinding.RowResultsHeaderBinding
import com.app.kinogame.databinding.RowTalonBinding
import com.app.kinogame.ui.util.DateUtil
import com.app.kinogame.ui.util.getResourceColor
import com.app.kinogame.ui.util.getResourceDrawable
import com.app.kinogame.ui.util.setVisibleOrGone
import com.app.kinogame.ui.util.setVisibleOrInvisible

class ResultAdapter : ListAdapter<GameResultUI, ResultAdapter.GameResultHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultHolder {
        return when(viewType){
            HEADER -> HeaderHolder(RowResultsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> BallsResultHolder(RowResultsBallsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when {
            item.header -> HEADER
            else -> BALLS
        }
    }

    override fun onBindViewHolder(holder: GameResultHolder, position: Int) {
        holder.bind(getItem(position))
    }

    abstract class GameResultHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(model: GameResultUI)
    }

    inner class BallsResultHolder(val binding: RowResultsBallsBinding) : GameResultHolder(binding)  {
        override fun bind(model: GameResultUI) {
            with(binding){
                val views = listOf(ball1, ball2, ball3, ball4, ball5, ball6, ball7)
                views.forEach { it.root.setVisibleOrInvisible(false) }
                model.numbers.forEachIndexed { i, number ->
                    views[i].root.setVisibleOrInvisible(true)
                    views[i].textNumber.text = number.number.toString()
                    views[i].viewBg.setBackgroundResource(if(number.bonus) R.drawable.ball_selected else R.drawable.ball_unselected)
                }
            }
        }

    }

    inner class HeaderHolder(val binding: RowResultsHeaderBinding) : GameResultHolder(binding)  {
        override fun bind(model: GameResultUI) {
            with(binding){
                textViewTitle.text = String.format(binding.root.context.getString(R.string.label_title_game_details), DateUtil.formatTimeResult(model.drawTime), model.drawId.toString())
            }
        }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GameResultUI>() {
            override fun areItemsTheSame(oldItem: GameResultUI, newItem: GameResultUI): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: GameResultUI, newItem: GameResultUI): Boolean {
                return oldItem == newItem
            }
        }

        const val HEADER = 1
        const val BALLS = 2
    }
}