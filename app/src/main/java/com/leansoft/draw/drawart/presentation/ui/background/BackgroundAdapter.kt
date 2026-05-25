package com.leansoft.draw.drawart.presentation.ui.background

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemBackgroundBinding
import com.leansoft.draw.drawart.databinding.ItemGroupBgBinding
import com.leansoft.draw.drawart.domain.model.BackgroundModel

class BackgroundAdapter(
    private val onClickBg: (Int) -> Unit
) :
    ListAdapter<BackgroundModel, BackgroundAdapter.ViewHolder>(DiffUtilBg()) {

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val binding = ItemGroupBgBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        p0.bind(getItem(p1))
    }

    inner class ViewHolder(private val binding: ItemGroupBgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BackgroundModel) {
            binding.txtTitle.text = item.title
            binding.rcvItems.apply {

                adapter =
                    BgItemAdapter { item ->
                        onClickBg(item)
                    }.apply {
                        submitList(item.listBg)
                    }
            }
        }
    }


    class DiffUtilBg : DiffUtil.ItemCallback<BackgroundModel>() {
        override fun areItemsTheSame(
            p0: BackgroundModel,
            p1: BackgroundModel
        ): Boolean {
            return p0 == p1
        }

        override fun areContentsTheSame(
            p0: BackgroundModel,
            p1: BackgroundModel
        ): Boolean {
            return p0.title == p1.title
        }
    }

}
