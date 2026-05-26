package com.leansoft.draw.drawart.presentation.ui.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemFramePreviewBinding
import com.leansoft.draw.drawart.domain.model.AnimationDetail
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.utils.ext.loadAsset
import com.leansoft.draw.drawart.utils.ext.loadImage

class FramePreviewAdapter(private val onItemClick: ((String, Int) -> Unit)? = null) :
    RecyclerView.Adapter<FramePreviewAdapter.ViewHolder>() {
    private var list: MutableList<String> = mutableListOf()
    override fun getItemCount() = list.size

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

        holder.bind(list[pos])
    }

    fun setList(list: List<String>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFramePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                ivFramePreview.loadAsset(item)
            }
        }
    }
}