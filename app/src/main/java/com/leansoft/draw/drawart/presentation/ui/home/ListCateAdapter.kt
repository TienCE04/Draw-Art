package com.leansoft.draw.drawart.presentation.ui.home

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.databinding.ItemTemplateDrawBinding
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.utils.ext.loadImage

class ListCateAdapter(
    private val context: Context,
    private val callback: (AnimationModel) -> Unit
) :
    ListAdapter<AnimationModel, ListCateAdapter.ViewHolder>(DiffUtilAnim()) {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ListCateAdapter.ViewHolder {
        val view =
            ItemTemplateDrawBinding.inflate(android.view.LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCateAdapter.ViewHolder, pos: Int) {
        val item = getItem(pos)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemTemplateDrawBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AnimationModel) {
            with(binding) {
                tvName.text = data.nameAnim
                tvLevel.text = data.level
                imgThumb.loadImage(data.urlGif)
                tvNumberFrame.text = context.getString(R.string.msg_number_frame, data.numberFrame)
            }
            itemView.setOnClickListener {
                callback.invoke(data)
            }
        }
    }

    class DiffUtilAnim : DiffUtil.ItemCallback<AnimationModel>() {
        override fun areItemsTheSame(
            p0: AnimationModel,
            p1: AnimationModel
        ): Boolean {
            return p0.idAnim == p1.idAnim
        }

        override fun areContentsTheSame(
            p0: AnimationModel,
            p1: AnimationModel
        ): Boolean {
            return p0 == p1
        }
    }
}
