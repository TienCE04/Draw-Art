package com.leansoft.draw.drawart.presentation.ui.home

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leansoft.draw.drawart.domain.model.AnimationPack
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel

class FragmentVPAdapter(
    private val fragment: Fragment
) : FragmentStateAdapter(fragment) {
    private val list = mutableListOf<AnimationPack>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<AnimationPack>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun createFragment(p0: Int): FragmentListCate {
        return FragmentListCate.newInstance(list[p0].category, list[p0].level)
    }

    override fun getItemCount() = list.size
}