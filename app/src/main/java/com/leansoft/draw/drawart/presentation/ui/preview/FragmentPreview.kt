package com.leansoft.draw.drawart.presentation.ui.preview

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentPreviewBinding
import com.leansoft.draw.drawart.presentation.ui.draw.FrameSmallAdapter
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.FrameAnimationPlayer
import com.leansoft.draw.drawart.utils.ext.loadImage
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPreview : BaseFragment<FragmentPreviewBinding, NothingViewModel>() {
    private val adapter: FrameSmallAdapter? = null
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        binding.rcvFrame.adapter = adapter
        register()
        observe()
    }

    private fun register() {
        with(binding) {
            modeDraw.safeOnClickListener {
                mainVM.preLoadBm()
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = false)
                navVM.navigate(directions)
            }
            modeTemplate.safeOnClickListener {
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = true)
                navVM.navigate(directions)
            }
        }
    }

    private fun observe() {
        mainVM.itemAnimSelected.observe(viewLifecycleOwner) { data ->
            data?.let {
                with(binding) {
                    imgPreview.loadImage(it.urlGif)
                    tvNumberFrame.text = it.numberFrame.toString()
                    imgThumbUse.loadImage(it.thumbnail, placeholder = R.drawable.img_thumb_temp)
                    imgThumbDraw.loadImage(
                        it.thumbnail,
                        alpha = 0.4f,
                        placeholder = R.drawable.img_thumb_temp
                    )
                    adapter?.submitList(data.listFrame)
                }
            }
        }
    }

    override fun initViewBinding(): FragmentPreviewBinding {
        return FragmentPreviewBinding.inflate(layoutInflater)
    }
}