package com.leansoft.draw.drawart.presentation.ui.draw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemFrameEdittedBinding
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.presentation.ui.preview.FramePreviewAdapter
import com.leansoft.draw.drawart.utils.ext.loadImage

class FrameSmallAdapter(private val onItemClick: ((FrameModel) -> Unit)? = null) :
    ListAdapter<FrameModel, FrameSmallAdapter.ViewHolder>(
        FramePreviewAdapter.DiffUtilFrame()
    ) {

    private var positionSelected = -1

    inner class ViewHolder(private val binding: ItemFrameEdittedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FrameModel, position: Int) {
            with(binding) {
                tvNumber.text = position.toString()

                val source =
                    item.pathFrameSmall.ifEmpty { item.urlFrame }

                ivFramePreview.loadImage(source)

                ivBorder.isSelected = position == positionSelected
                root.setOnClickListener {
                    val oldPos = positionSelected
                    positionSelected = bindingAdapterPosition

                    notifyItemChanged(oldPos)
                    notifyItemChanged(positionSelected)
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = ItemFrameEdittedBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), position + 1)
    }
}