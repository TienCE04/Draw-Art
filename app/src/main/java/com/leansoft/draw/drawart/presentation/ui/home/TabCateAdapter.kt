package com.leansoft.draw.drawart.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemTabCateBinding
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel

class TabCateAdapter :
    ListAdapter<CategoryGroupModel, TabCateAdapter.ViewHolder>(DiffUtilTabCate()) {
    inner class ViewHolder(private val binding: ItemTabCateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryGroupModel) {
            binding.tvName.text = item.nameCategory
        }
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = ItemTabCateBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        p0.bind(getItem(p1))
    }

    class DiffUtilTabCate : DiffUtil.ItemCallback<CategoryGroupModel>() {
        override fun areItemsTheSame(
            p0: CategoryGroupModel,
            p1: CategoryGroupModel
        ): Boolean {
            return p0.nameCategory == p1.nameCategory
        }

        override fun areContentsTheSame(
            p0: CategoryGroupModel,
            p1: CategoryGroupModel
        ): Boolean {
            return p0 == p1
        }
    }
}