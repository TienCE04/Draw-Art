package com.leansoft.draw.drawart.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemTabCateBinding
import com.leansoft.draw.drawart.domain.model.AnimationPack

class TabCateAdapter(
    private val clickTab: (String, Int) -> Unit
) : ListAdapter<AnimationPack, TabCateAdapter.ViewHolder>(DiffUtilTabCate()) {

    private var positionSelected = 0

    inner class ViewHolder(
        private val binding: ItemTabCateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimationPack) {

            binding.tvName.text = item.category

            binding.ivBottom.visibility =
                if (positionSelected == bindingAdapterPosition) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }

            itemView.setOnClickListener {

                val oldPos = positionSelected
                positionSelected = bindingAdapterPosition

                notifyItemChanged(oldPos)
                notifyItemChanged(positionSelected)

                clickTab.invoke(item.category, positionSelected)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemTabCateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class DiffUtilTabCate : DiffUtil.ItemCallback<AnimationPack>() {

        override fun areItemsTheSame(
            oldItem: AnimationPack,
            newItem: AnimationPack
        ): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(
            oldItem: AnimationPack,
            newItem: AnimationPack
        ): Boolean {
            return oldItem == newItem
        }
    }
}