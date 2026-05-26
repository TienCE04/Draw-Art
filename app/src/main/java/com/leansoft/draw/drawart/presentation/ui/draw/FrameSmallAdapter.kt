package com.leansoft.draw.drawart.presentation.ui.draw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemFrameEdittedBinding
import com.leansoft.draw.drawart.domain.model.AnimationDetail
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.presentation.ui.preview.FramePreviewAdapter
import com.leansoft.draw.drawart.utils.ext.loadAsset
import com.leansoft.draw.drawart.utils.ext.loadImage

class FrameSmallAdapter(private val onItemClick: ((String, Int) -> Unit)? = null) :
    RecyclerView.Adapter<FrameSmallAdapter.ViewHolder>(
    ) {

        private var list: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int {
        return list.size
    }

    private var positionSelected = -1

    inner class ViewHolder(private val binding: ItemFrameEdittedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: String, position: Int) {
            with(binding) {
                tvNumber.text = (position + 1).toString()

                ivFramePreview.loadAsset(item)

                ivBorder.isSelected = position == positionSelected
                root.setOnClickListener {
                    val oldPos = positionSelected
                    positionSelected = bindingAdapterPosition

                    notifyItemChanged(oldPos)
                    notifyItemChanged(positionSelected)
                    onItemClick?.invoke(item, position)
                }
            }
        }
    }

    fun setList(list: List<String>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
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
        holder.bind(list[position], position)
    }
}