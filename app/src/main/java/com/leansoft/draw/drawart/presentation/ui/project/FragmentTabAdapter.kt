package com.leansoft.draw.drawart.presentation.ui.project

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(p0: Int): Fragment {
        return when (p0) {
            0 -> FragmentProjectDetail.newInstance(TypeProject.PROJECT)
            else -> FragmentProjectDetail.newInstance(TypeProject.MOVIE)
        }
    }

    override fun getItemCount() = 2
}

