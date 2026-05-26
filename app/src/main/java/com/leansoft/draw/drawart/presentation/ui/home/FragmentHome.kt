package com.leansoft.draw.drawart.presentation.ui.home

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentHomeBinding
import com.leansoft.draw.drawart.databinding.ItemTabCateBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.AnimationTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding, NothingViewModel>() {
    private var adapter: FragmentVPAdapter? = null
    private var titles: List<String> = emptyList()
    private var mediator: TabLayoutMediator? = null

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        adapter = FragmentVPAdapter(this)
        with(binding) {
            vpCate.adapter = adapter
        }

        observe()
    }


    private fun setupViewPagerAndTab() {

        binding.vpCate.adapter = adapter

        mediator?.detach()

        mediator = TabLayoutMediator(binding.tabLayout, binding.vpCate) { tab, position ->
            val itemBinding = ItemTabCateBinding.inflate(layoutInflater)

            itemBinding.tvName.text = titles[position]

            // default trạng thái
            itemBinding.ivBottom.visibility = View.GONE

            tab.customView = itemBinding.root
        }

        mediator?.attach()

        binding.tabLayout.post {
            updateTabState(binding.tabLayout.getTabAt(0),true)
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTabState(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabState(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    private fun updateTabState(tab: TabLayout.Tab?, selected: Boolean) {
        val view = tab?.customView ?: return
        val iv = view.findViewById<ImageView>(R.id.ivBottom)

        iv.visibility = if (selected) View.VISIBLE else View.GONE
    }

    private fun observe() {
        mainVM.animations.observe(viewLifecycleOwner) { data ->
            adapter?.submitList(data)
            titles = data
                .map { it.category }
            Log.d("DEBUG", "observe rt: $titles")
            setupViewPagerAndTab()
        }
    }

    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            while (true) {
                logRam()
                delay(2000)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        AnimationTicker.clear()
    }

    private fun logRam() {
        val activityManager =
            requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val availMem = memoryInfo.availMem / (1024 * 1024)
        val totalMem = memoryInfo.totalMem / (1024 * 1024)
        val debugInfo = Debug.MemoryInfo()
        Debug.getMemoryInfo(debugInfo)
        val appRam = debugInfo.totalPss / 1024
        Log.d("RAM", "Available: ${availMem}MB / Total: ${totalMem}MB / App: ${appRam}MB")
    }
}