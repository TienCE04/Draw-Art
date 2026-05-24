package com.leansoft.draw.drawart.presentation.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemFpsBinding

class FpsAdapter(private val items: List<Int>) : RecyclerView.Adapter<FpsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFpsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fps: Int) {
            binding.tvFps.text = fps.toString()
        }
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val binding = ItemFpsBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        p0.bind(items[p1])
    }

    override fun getItemCount() = items.size
}