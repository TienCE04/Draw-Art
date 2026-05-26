package com.leansoft.draw.drawart.presentation.ui.create

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentRateBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.FrameAnimationPlayer
import com.leansoft.draw.drawart.utils.dp
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import timber.log.Timber

class FragmentRate : BaseFragment<FragmentRateBinding, NothingViewModel>() {
    private val createVM: CreateViewModel by navGraphViewModels(R.id.create_graph) {
        defaultViewModelProviderFactory
    }

    private val frameAnimationPlayer = FrameAnimationPlayer()
    var fpsSelected = 12
    private lateinit var snapHelper: LinearSnapHelper

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        with(binding) {
            layoutHeader.ivLogo.safeOnClickListener {
                navVM.back()
            }
            layoutHeader.tvHeader.text = getString(R.string.msg_frame_per_second)
        }
        frameAnimationPlayer.startAnimation(
            binding.imgFrame,
            emptyList(),
            fpsSelected
        )
        setUpRecycleView()
        register()
        observe()
    }

    private fun setUpRecycleView() {

        val fpsList = (1..60).toList()
        val adapter = FpsAdapter(fpsList)
        binding.rcvFps.adapter = adapter

        //snap helper
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rcvFps)

        binding.rcvFps.post {

            val itemHeight = 40.dp

            val padding =
                binding.rcvFps.height / 2 - itemHeight / 2

            binding.rcvFps.setPadding(
                0,
                padding,
                0,
                padding
            )
        }

        listenSelectedItem(fpsList)

    }

    private fun listenSelectedItem(
        list: List<Int>
    ) {

        val layoutManager =
            binding.rcvFps.layoutManager as LinearLayoutManager

        binding.rcvFps.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(
                        recyclerView,
                        newState
                    )

                    if (newState ==
                        RecyclerView.SCROLL_STATE_IDLE
                    ) {

                        val snapView =
                            snapHelper.findSnapView(layoutManager)

                        snapView?.let {

                            val position =
                                layoutManager.getPosition(it)

                            fpsSelected = list[position]

                            frameAnimationPlayer.startAnimation(
                                binding.imgFrame,
                                createVM.listBitmapTemp.value ?: emptyList(),
                                fpsSelected
                            )

                            Timber.tag("FPS").d("Selected = $fpsSelected")
                        }
                    }
                }
            }
        )
    }

    private fun register() {
        with(binding) {
            layoutHeader.ivLogo.safeOnClickListener {
                val bundle = Bundle().apply {
                    putInt("frame_per_second", fpsSelected)
                }
                setFragmentResult("fps_result", bundle)
                navVM.back()
            }
        }
    }

    private fun observe() {
        createVM.listBitmapTemp.observe(viewLifecycleOwner) {

        }
    }

    override fun initViewBinding(): FragmentRateBinding {
        return FragmentRateBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        frameAnimationPlayer.stop()
    }
}