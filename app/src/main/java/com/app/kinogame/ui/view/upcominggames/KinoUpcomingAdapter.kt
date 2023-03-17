package com.app.kinogame.ui.view.upcominggames

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kinogame.R
import com.app.kinogame.data.uimodel.KinoUpcomingUI
import com.app.kinogame.databinding.RowKinoUpcomingBinding
import com.app.kinogame.ui.util.DateUtil
import com.app.kinogame.ui.util.getResourceColor

class KinoUpcomingAdapter(private val drawEvent: (Int, Long) -> Unit) : ListAdapter<KinoUpcomingUI, KinoUpcomingAdapter.KinoUpcomingHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KinoUpcomingHolder {
        return KinoUpcomingHolder(RowKinoUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KinoUpcomingHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class KinoUpcomingHolder(val binding: RowKinoUpcomingBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(model: KinoUpcomingUI) {
            with(binding) {
                root.setOnClickListener {
                    drawEvent.invoke(model.drawId, model.drawTime)
                }
                textDrawId.text = DateUtil.formatTimeResult(model.drawTime)
                textTime.text = DateUtil.format(model.diff())
                textTime.setTextColor(root.context.getResourceColor(if(model.isSoonStart()) R.color.red else R.color.white))
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KinoUpcomingUI>() {
            override fun areItemsTheSame(oldItem: KinoUpcomingUI, newItem: KinoUpcomingUI): Boolean {
                return oldItem.drawId == newItem.drawId
            }
            override fun areContentsTheSame(oldItem: KinoUpcomingUI, newItem: KinoUpcomingUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}