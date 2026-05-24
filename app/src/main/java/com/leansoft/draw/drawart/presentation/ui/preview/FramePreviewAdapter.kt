package com.leansoft.draw.drawart.presentation.ui.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemFramePreviewBinding
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.utils.ext.loadImage

class FramePreviewAdapter :
    ListAdapter<FrameModel, FramePreviewAdapter.ViewHolder>(DiffUtilFrame()) {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = ItemFramePreviewBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        pos: Int
    ) {

        holder.bind(getItem(pos))
    }

    inner class ViewHolder(private val binding: ItemFramePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FrameModel) {
            with(binding) {
                ivFramePreview.loadImage(item.urlFrame)
            }

        }
    }

    class DiffUtilFrame : DiffUtil.ItemCallback<FrameModel>() {
        override fun areItemsTheSame(
            p0: FrameModel,
            p1: FrameModel
        ): Boolean {
            return p0.idFrame == p1.idFrame
        }

        override fun areContentsTheSame(
            p0: FrameModel,
            p1: FrameModel
        ): Boolean {
            return p0 == p1
        }
    }
}