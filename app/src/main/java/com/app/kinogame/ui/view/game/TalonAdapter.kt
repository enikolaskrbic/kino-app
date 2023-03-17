package com.app.kinogame.ui.view.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kinogame.R
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.databinding.RowKinoUpcomingBinding
import com.app.kinogame.databinding.RowTalonBinding
import com.app.kinogame.ui.util.DateUtil
import com.app.kinogame.ui.util.getResourceColor
import com.app.kinogame.ui.util.getResourceDrawable

class TalonAdapter(private val numberEvent: (TalonUI) -> Unit) : ListAdapter<TalonUI, TalonAdapter.TalonHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonHolder {
        return TalonHolder(RowTalonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TalonHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TalonHolder(val binding: RowTalonBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(model: TalonUI) {
            with(binding) {
                root.setOnClickListener {
                    numberEvent.invoke(model)
                }
                textNumber.text = model.number.toString()
                textNumber.setTextColor(root.context.getResourceColor(if(model.selected) R.color.yellow else R.color.white))
                viewBg.setBackgroundResource(if(model.selected) R.drawable.ball_selected else R.drawable.ball_unselected)
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TalonUI>() {
            override fun areItemsTheSame(oldItem: TalonUI, newItem: TalonUI): Boolean {
                return oldItem.number == newItem.number
            }
            override fun areContentsTheSame(oldItem: TalonUI, newItem: TalonUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}