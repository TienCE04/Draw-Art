package com.leansoft.draw.drawart.presentation.ui.preview

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentPreviewBinding
import com.leansoft.draw.drawart.presentation.ui.draw.FrameSmallAdapter
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.FrameAnimationPlayer
import com.leansoft.draw.drawart.utils.ext.loadAsset
import com.leansoft.draw.drawart.utils.ext.loadImage
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPreview : BaseFragment<FragmentPreviewBinding, PreviewVM>() {
    private var animJob: Job? = null
    private var adapter: FramePreviewAdapter? = null
    override fun getClassVM(): Class<PreviewVM> {
        return PreviewVM::class.java
    }

    override fun initView() {
        adapter = FramePreviewAdapter { item, position -> }
        binding.rcvFrame.adapter = adapter
        register()
        observe()
    }

    private fun register() {
        with(binding) {
            layoutHeader.tvHeader.text = getString(R.string.msg_preview)
            modeDraw.safeOnClickListener {
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = false)
                navVM.navigate(directions)
            }
            modeTemplate.safeOnClickListener {
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = true)
                navVM.navigate(directions)
            }
            layoutHeader.ivLogo.safeOnClickListener {
                navVM.back()
            }
        }
    }

    private fun observe() {
        mainVM.itemAnimSelected.observe(viewLifecycleOwner) { data ->
            data?.let {
                with(binding) {
                    tvNumberFrame.text = it.listFrame.size.toString()
                    imgThumbUse.loadAsset(it.listFrame[0], placeholder = R.drawable.img_thumb_temp)
                    imgThumbDraw.loadAsset(
                        it.listFrame[0],
                        alphaValue = 0.4f,
                        placeholder = R.drawable.img_thumb_temp
                    )
                    Log.d("DEBUG", "observe-list: ${it.listFrame}")
                    adapter?.setList(it.listFrame)
                    mViewModel.preloadBitmap(it.listFrame)
                }
            }
        }
        mViewModel.stateLoadBm.observe(viewLifecycleOwner) {
            if (it) {
                startAnimation(mainVM.itemAnimSelected.value?.listFrame ?: emptyList())
            }
        }
    }


    private fun startAnimation(listFrame: List<String>) {
        if (listFrame.isEmpty()) return
        val fps = 12
        val delayTime = 1000L / fps
        animJob?.cancel()
        animJob = viewLifecycleOwner.lifecycleScope.launch {
            var index = 0

            while (isActive) {
                val bitmap = mViewModel.getFrame(listFrame[index])
                bitmap?.let {
                    binding.imgPreview.setImageBitmap(it)
                }
                index = (index + 1) % listFrame.size
                delay(delayTime)
            }
        }

    }

    override fun initViewBinding(): FragmentPreviewBinding {
        return FragmentPreviewBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animJob?.cancel()
    }
}