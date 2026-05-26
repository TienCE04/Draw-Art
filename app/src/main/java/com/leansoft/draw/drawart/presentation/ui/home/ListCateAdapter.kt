package com.leansoft.draw.drawart.presentation.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.databinding.ItemTemplateDrawBinding
import com.leansoft.draw.drawart.domain.model.AnimationDetail
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.utils.AnimationTicker
import com.leansoft.draw.drawart.utils.ext.loadImage

class ListCateAdapter(
    private val level: String,
    private val callback: (AnimationDetail) -> Unit
) :
    ListAdapter<AnimationDetail, ListCateAdapter.AnimViewHolder>(DiffUtilAnim()) {

    private lateinit var context: Context
    private val bitmapCache =
        mutableMapOf<String, Bitmap>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): ListCateAdapter.AnimViewHolder {
        context = parent.context
        val view =
            ItemTemplateDrawBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return AnimViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCateAdapter.AnimViewHolder, pos: Int) {
        val item = getItem(pos)
        holder.bind(item)
    }

    inner class AnimViewHolder(
        private val binding: ItemTemplateDrawBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var data: AnimationDetail? = null

        fun bind(item: AnimationDetail) {

            data = item

            binding.tvName.text = item.nameAnim
            binding.tvLevel.text = level
            binding.tvNumberFrame.text =
                item.listFrame.size.toString()

            AnimationTicker.register(this)

            itemView.setOnClickListener {
                callback.invoke(item)
            }
        }

        fun render(globalFrame: Int) {

            val item = data ?: return

            val frameIndex =
                globalFrame % item.listFrame.size

            val path = item.listFrame[frameIndex]

            val bitmap = getBitmap(path)

            binding.imgThumb.setImageBitmap(bitmap)
        }
        fun stop() {
            AnimationTicker.unregister(this)
        }

    }
    private fun getBitmap(path: String): Bitmap? {

        bitmapCache[path]?.let {
            return it
        }

        return try {

            val options = BitmapFactory.Options().apply {

                // giảm RAM
                inPreferredConfig =
                    Bitmap.Config.RGB_565
            }

            val bitmap = context.assets
                .open(path)
                .use {
                    BitmapFactory.decodeStream(
                        it,
                        null,
                        options
                    )
                }

            if (bitmap != null) {

                bitmapCache[path] = bitmap
            }

            bitmap

        } catch (e: Exception) {

            null
        }
    }

    override fun onViewRecycled(holder: AnimViewHolder) {
        super.onViewRecycled(holder)

        holder.stop()
    }
    class DiffUtilAnim : DiffUtil.ItemCallback<AnimationDetail>() {
        override fun areItemsTheSame(
            p0: AnimationDetail,
            p1: AnimationDetail
        ): Boolean {
            return p0.nameAnim == p1.nameAnim
        }

        override fun areContentsTheSame(
            p0: AnimationDetail,
            p1: AnimationDetail
        ): Boolean {
            return p0 == p1
        }
    }
}
