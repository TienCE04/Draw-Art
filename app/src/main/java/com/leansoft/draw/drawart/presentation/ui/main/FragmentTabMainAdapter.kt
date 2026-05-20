package com.leansoft.draw.drawart.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leansoft.draw.drawart.presentation.ui.home.FragmentHome
import com.leansoft.draw.drawart.presentation.ui.project.FragmentProject

class FragmentTabMainAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(p0: Int): Fragment {
        return when (p0) {
            0 -> FragmentHome()
            else -> FragmentProject()
        }
    }

    override fun getItemCount() = 2
}