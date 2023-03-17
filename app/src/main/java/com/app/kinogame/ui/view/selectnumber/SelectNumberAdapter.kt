package com.app.kinogame.ui.view.selectnumber

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kinogame.data.uimodel.TalonUI
import com.app.kinogame.databinding.RowSelectNumberBinding

class SelectNumberAdapter(private val numberEvent: (Int) -> Unit) : ListAdapter<Int, SelectNumberAdapter.NumberHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        return NumberHolder(RowSelectNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class NumberHolder(val binding: RowSelectNumberBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(model: Int) {
            with(binding) {
                binding.buttonRandom.setOnClickListener {
                    numberEvent.invoke(model)
                }
                binding.buttonRandom.text = model.toString()
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
}