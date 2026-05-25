package com.leansoft.draw.drawart.presentation.ui.background

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemBackgroundBinding
import com.leansoft.draw.drawart.utils.ext.loadImage
import com.leansoft.draw.drawart.utils.ext.visibleOrGone

class BgItemAdapter(private val onClickBg: (Int) -> Unit) :
    ListAdapter<Int, BgItemAdapter.ViewHolder>(DiffUtilBg()) {

    private var positionSelected = -1
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val binding = ItemBackgroundBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        p0.bind(getItem(p1), p1)
    }

    inner class ViewHolder(private val binding: ItemBackgroundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int, pos: Int) {
            binding.imgBackground.loadImage(item)
            binding.ivBorder.visibleOrGone(positionSelected == pos)
            binding.root.setOnClickListener {
                val oldPos = positionSelected
                positionSelected = pos

                notifyItemChanged(oldPos)
                notifyItemChanged(positionSelected)
                onClickBg(item)
            }

        }
    }

    class DiffUtilBg : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}